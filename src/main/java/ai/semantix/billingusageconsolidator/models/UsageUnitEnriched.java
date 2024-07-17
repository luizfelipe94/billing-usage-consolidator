package ai.semantix.billingusageconsolidator.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsageUnitEnriched {

  private long timestamp;
  private String measure;
  private String size;
  private long usage;
  private float cost;
  private String message;
  private String orgId;
  private String orgName;
  private String tenantId;
  private String tenantName;
  private String moduleId;
  private String moduleName;
  
}
