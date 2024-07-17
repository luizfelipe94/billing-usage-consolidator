package luizfelipe94.billingusageconsolidator.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricingTable {

  private String module;
  private String measure;
  private String size;
  private Float price;
  
}
