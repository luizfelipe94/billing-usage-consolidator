package ai.semantix.billingusageconsolidator.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsageAggregator {
  
  private String orgId;
  private String orgName;
  private String tenantId;
  private String tenantName;
  private String moduleId;
  private String moduleName;
  private String measure;
  private String size;
  private long timestamp;
  
  private long totalUsage;
  private float totalCost;

}
