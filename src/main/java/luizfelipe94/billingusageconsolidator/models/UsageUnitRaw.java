package luizfelipe94.billingusageconsolidator.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
class Org {
  private String id;
  private String name;
}

@Data
class Tenant {
  private String id;
  private String name;
}

@Data
class Product {
  private String id;
  private String name;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsageUnitRaw {

  private long timestamp;
  private String measure;
  private String size;
  private long usage;
  private Org org;
  private Tenant tenant;
  private Product product;

  public String getOrgId() {
    return org.getId();
  }

  public String getOrgName() {
    return org.getName();
  }

  public String getTenantId() {
    return tenant.getId();
  }

  public String getTenantName() {
    return tenant.getName();
  }

  public String getProductId() {
    return product.getId();
  }

  public String getProductName() {
    return product.getName();
  }
  
}
