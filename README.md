# BILLING USAGE CONSOLIDATOR

POC to aggregate usage and cost by aggregating billing events using Kafka and Kafka Streams.

![](./assets/image.png)


### Messages

```json

# pricing
{
    "product": "tron",
    "measure": "api",
    "size": "get",
    "price": 0.0003
}

# usage
{
  "timestamp": 1744932056,
  "measure": "tron",
  "size": "get",
  "usage": 500,
  "org": {
    "id": "i",
    "name": "cloudinha"
  },
  "tenant": {
    "id": "1",
    "name": "tenant1"
  },
  "product": {
    "id": "1",
    "name": "tron"
  }
}
```