package com.nacho.blog.springalternatives.fulldemo.service;

import com.nacho.blog.springalternatives.fulldemo.config.PersistenceModule;
import com.nacho.blog.springalternatives.fulldemo.controller.dto.CreateShipmentRequest;
import com.nacho.blog.springalternatives.fulldemo.model.Shipment;
import com.nacho.blog.springalternatives.fulldemo.model.User;
import com.nacho.blog.springalternatives.fulldemo.repository.ProductRepository;
import com.nacho.blog.springalternatives.fulldemo.repository.ShipmentRepository;
import com.nacho.blog.springalternatives.fulldemo.repository.UserRepository;
import com.nacho.blog.springalternatives.fulldemo.repository.impl.ProductRepositoryImpl;
import com.nacho.blog.springalternatives.fulldemo.repository.impl.ShipmentRepositoryImpl;
import com.nacho.blog.springalternatives.fulldemo.repository.impl.UserRepositoryImpl;
import dagger.Binds;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class ShipmentServiceIntegrationTest {

  static {
    System.setProperty("profile", "test");
  }

  private ShipmentService shipmentService;
  private ProductService productService;
  private ShipmentRepository shipmentRepository;
  private UserApi mockUserApi;

  @BeforeAll
  public static void setup() throws IOException {
    DaggerShipmentServiceIntegrationTest_TestComponent.create().sqlRunner().run("src/test/resources/init-h2.sql");
  }

  @BeforeEach
  public void clear() {
    TestComponent testComponent = DaggerShipmentServiceIntegrationTest_TestComponent.create();
    shipmentService = testComponent.shipmentService();
    productService = testComponent.productService();
    mockUserApi = testComponent.userApi();
    shipmentRepository = testComponent.shipmentRepository();
    shipmentService.clearShipments();
  }

  @Test
  @DisplayName("Should save shipment")
  void testSaveShipment() throws IOException {

    // given
    var call = mock(Call.class);
    when(call.execute()).thenReturn(Response.success(new User(1, "test@test.com")));
    when(mockUserApi.getById(anyInt())).thenReturn(call);

    final var product1 = productService.createProduct("p1", BigDecimal.TEN);
    final var product2 = productService.createProduct("p2", BigDecimal.TEN);

    // when
    final List<CreateShipmentRequest.ItemRequest> items = List.of( //
            CreateShipmentRequest.ItemRequest.builder().product(product1.getId()).quantity(2).build(),
            CreateShipmentRequest.ItemRequest.builder().product(product2.getId()).quantity(1).build());

    final UUID shipment = shipmentService.createShipment(new CreateShipmentRequest(1, items)).getId();

    // then
    final Shipment createdShipment = shipmentService.getById(shipment);
    assertThat(createdShipment).isNotNull();
    assertThat(createdShipment.getTotal()).isEqualByComparingTo(new BigDecimal(30));
    assertThat(createdShipment.getItems()).size().isEqualTo(2);
    assertThat(shipmentService.getShipmentCount()).isEqualTo(1);

    assertThat(shipmentService.getShipmentCount()).isEqualTo(1);
  }

  @Test
  @DisplayName("Should rollback on error shipment")
  void testFailSaveShipment() throws IOException {

    // given
    var call = mock(Call.class);
    when(call.execute()).thenReturn(Response.success(new User(1, "test@test.com")));
    when(mockUserApi.getById(anyInt())).thenReturn(call);
    doThrow(RuntimeException.class).when(shipmentRepository).insertShipmentItems(any(Shipment.class));

    final var product1 = productService.createProduct("p1", BigDecimal.TEN);
    final var product2 = productService.createProduct("p2", BigDecimal.TEN);

    // when
    final List<CreateShipmentRequest.ItemRequest> items = List.of( //
            CreateShipmentRequest.ItemRequest.builder().product(product1.getId()).quantity(2).build(),
            CreateShipmentRequest.ItemRequest.builder().product(product2.getId()).quantity(1).build());

    assertThatThrownBy(() -> shipmentService.createShipment(new CreateShipmentRequest(1, items)))
            .isInstanceOf(RuntimeException.class);

    // then
    assertThat(shipmentService.getShipmentCount()).isEqualTo(0);
  }

  @Singleton
  @Component(modules = {TestModule.class, PersistenceModule.class})
  interface TestComponent {
    ShipmentService shipmentService();

    ProductService productService();

    ShipmentRepository shipmentRepository();

    UserApi userApi();

    SqlRunner sqlRunner();
  }

  @Module
  static abstract class TestModule {

    @Binds
    public abstract ProductRepository productRepositoryImpl(ProductRepositoryImpl productRepositoryImpl);

    @Binds
    public abstract UserRepository userRepositoryImpl(UserRepositoryImpl userRepositoryImpl);

    @Singleton
    @Provides
    public static ShipmentRepository shipmentRepository(DSLContext dslContext) {
      return spy(new ShipmentRepositoryImpl(dslContext));
    }

    @Singleton
    @Provides
    public static UserApi userApi() {
      return mock(UserApi.class);
    }
  }

  @Singleton
  static class SqlRunner {

    private final DSLContext dslContext;

    @Inject
    SqlRunner(DSLContext dslContext) {
      this.dslContext = dslContext;
    }

    void run(String filePath) throws IOException {
      Path path = Paths.get(filePath);
      String sql = String.join("\n", Files.readAllLines(path));
      dslContext.execute(sql);
    }
  }
}
