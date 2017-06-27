 # Target reference


In this section the format of target definitions is specified. 

#### Target restrictions model


Unless otherwise specified, each target type may be expressed as a set of consecutive restrictions:
* total-guaranteed  - the guaranteed quantity of a resource to be granted to the user group in total  
* total-limit  - the limit a resource for the user group in total  
* instance-guaranteed- the guaranteed quantity of a resource to be granted to each instance (virtual machine or Docker container in case of compute service type)
* instance-limit - the limit of a resource for each instance
* user-guaranteed - the guaranteed quantity of a resource to be granted to each user
* user-limit  - the limit of a resource for each user


If a restriction is not speciffied, we assume following default values:
* total-guaranteed -  0 (no guarantees for group)
* total-limit - inf (no limit for group)
* instance-guaranteed - 0 (no guarantees per instance)
* instance-limit - inf (no limit per instance)
* user-guaranteed - 0 (no guarantees for a user)
* user-limit  - inf (no limit for a user)
Compute service target types reference


#### Target types conforming to the restrictions model

| Type | Unit | Description |
| --- | --- | --- |
| computing_time | h | Computing time restrictions in hours |
| num_cpus | none | CPU number restrictions
| mem_size | MB |  Operating memory (RAM) size restrictions in megabytes
| disk_size | MB |  Storage memory size restrictions in megabytes
| public_ip | none |  Number of IP adresses restrictions
| upload_bandwith | Mbps |  Upload bandwidth restrictions in megebytes per second
| download_bandwith | Mbps |  Download bandwidth restrictions in megebytes per second
| upload_aggregated | MB |  Total uploaded data size restrictions in megabytes
| download_aggregated | MB |  Total downloaded data size restrictions in megabytes

#### Target types not conforming to the restrictions model

| Type | Unit | Description | Restrictions
| --- | --- | --- |
| costs | EUR |  Compute service costs restriction. | limit-total, limit-instance, limit-user
| cpu_frequency | ? |  Restrictions for frequency of virtualized CPU unit | none |


#### Samples
```javascript
targets: [
    {
      type: computing_time,
      unit: h,
      restrictions: {    
        guaranteed-total: 10, // 10 computing hours is guaranteed to be granted to the user group
        limit-total: 100, // the user group can use up to 100 computing hours
        guaranteed-instance: 2, // 2 computing hours is guaranteed to be granted to each virtual machine or Docker container (instance)
        limit-instance: 100, // each virtual machine or Docker container (instance) can use up to 100 computing hours
        guaranteed-user: 4, // 4 computing hours is guaranteed to be granted to each user belonging to the group (across all instances owned by the user)
        limit-user: 100 // each user in the group can use up to 100 computing hours (in this case each user can use the whole computing time quota of a group)
      }
    },
    {
      type: num_cpus,
      unit: null,
      restrictions: {    
        limit-total: 50, // the user group can use up to 50 cpu units at the same time
        limit-instance: 4, //  a virtual machine/container can use up to 4 cpu units at the same time
        limit-user: 6  // a single user can use up to 6 cpu units at the same time
      }
    }
}
```
