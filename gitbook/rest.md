# REST API specification


Service Level Agreement Manager (SLAM) exposes an interface for programmatic use in order to integrate other Indigo Data Cloud components. This documentation specifies the usage of REST API through HTTP protocol. 

## Resource *sla*

#### Endpoints
* list:`<slam-url>/sla `
* retrieve:`<slam-url>/sla/<id>`


#### Methods

**GET**

#### Parameters

*  customer - identifier of a customer (comma separated values allowed),
*  provider - identifier of a provider (comma separated values allowed),
*  date - SLAs active at date (URL encoded time - refer to ISO 8601 standard) - when not set current time is assumed,
*  ids - comma separated identifiers,
*  service_type - service type enumeration (comma separated values allowed - OR logical operator applied)

#### Response format

```javascript
[
    {
            id: <slam_id>,
            customer: <cmdb_id>,
            provider: <cmdb_id>,
            start_date: <datetime>,
            end_date: <datetime>,
            issue_date, <datetime>,
            services: [
                    {
                            ref_id: <cmdb_id>,
                            type: <service_type>,
                            targets: [                   
                                    ... // target definitions - refer to the Target reference
                            ]
                    }
            ]
    },
    ...
]
```

#### Request examples


* list 
  * ` https:/<slam_api_domain>/sla/ `
* retrieve
  *  ` https://<slam_api_domain>/sla/sla_1 `
* filter used (SLAs for customer123 active at current time)
  *  ` https://<slam_api_domain>/sla?customer=customer123 `
* filter used (SLAs for provider123 active at given time)
  *  ` https://<slam_api_domain>/sla?provider=provider123&date=2016-02-24T14%3A23%3A10Z `
* filter used (SLAs with ids - useful for combining with preferences resource response)
  *  ` https://<slam_api_domain>/sla?ids=sla_1,sla_2 `




#### Response example:
```javascript
[
  {
    id: sla_11,
    customer: customer123,
    provider: provider123,
    start_date: 11.01.2016+15:50:00,
    end_date: 11.02.2016+15:50:00,
    services: [
      {
        type: compute,
        ref_id: compute123,
        targets: [
          {           
            type: public_ip, 
            unit: none,
            restrictions: {
              max_total: 100,
              total_guaranteed: 10,
              ...
            },
              ...
          }, 
          ...
        ]
      },
      {
        type: storage,
        ref_id: storage123,
        targets: [
                ... 
        ]
      }
    ]
  },
  ...
]
```

## Resource *preferences*


#### Endpoints
* list:`<slam-url>/preferences`
* retrieve:`<slam-url>/preferences/<customer_id>`


#### Methods
**GET**

#### Parameters
none


#### Response format

```javascript
[
  {
    customer: <customer_id>,
    preferences: [
      {    
        service_type: <service_type>,
        priority: [
          {
             sla_id: <sla_id>,
             service_id: <cmdb_service_ref_id>,
             weight: <float 0..1>
          },
          ...
        ]
      },
      ...
    ]
  },
  ...
]
```

#### Request examples
* list preferences for two user active at date
  * `<slam-url>/preferences?customer=12,21&date=2016-02-24T14%3A23%3A10Z`


#### Response example


```javascript
[
  {     
    customer: 12,
    preferences: [
      {
        service_type: compute,
        priority: [
          {
            sla_id: sla_11,
            service_id: occi_1
            weight: 1.0
          },
          {
            sla_id: sla_15,
            service_id: occi_3,
            weight: 0.3
          },
          ...
        ]
      },
      {
        service_type: storage,
        priority: [
          {
            sla_id: sla_11,
            service_id: storage_1,
            weight: 0.5
          },
          {
            sla_id: sla_11,
            service_id: storage_1,
            weight: 0.5
          },
          ...
        ]
      }
    ]
  },
  {     
    customer: 14,
    preferences: [
            ...
    ]
  }
]
```


