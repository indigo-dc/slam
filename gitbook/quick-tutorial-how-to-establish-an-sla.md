# Quick tutorial: how to establish an SLA 

Assumptions: you have properly deployed SLAM instance at slam.your.url 

## **Request SLA as a customer**

To have customer role in SLAM you need to have account IAM connected to you SLAM instance. 

1. Open [https://](https://<dockerhost>:<port>/)slam.your.url[/](https://<dockerhost>:<port>/) and log-in in IAM 

1. Click “Add document” to make new request, fill the form \(most of the the metrics are optional\), but fill name, start, end dates, and at least one metric\)
2. As “Site” you can choose one of the “service” registered in CMDB
3. Click “Create”
4. In the dashboad you should have new request on the list of draft
5. Open it and click “Send to provider”

# **Accept the request as a provider**

Provider role is a role the enable to act as providers for all services. To have this role assigned you need to have PROVIDER\_EMAIL configured in the SLAM instance and have the same email registered in IAM. 

1. Open [https://](https://<dockerhost>:<port>/)slam.your.url[/](https://<dockerhost>:<port>/) and log-in in IAM with the account configured in “PROVIDER\_EMAIL” option

2. Go to: [https://](https://<dockerhost>:<port>/)slam.your.url[/](https://<dockerhost>:<port>/)[\#/dashboard?type=providerDashboard](https://10.13.13.3:8443/#/dashboard?type=providerDashboard)

3. You should see new request in “SLAs in negotiations” section \(the one created in previous step by a customer\)

4. Open it and click “Accept SLA”

# **Review preferences here:**

To check your preferences and SLAs, you should visit

[https://](https://<dockerhost>:<port>/rest/slam/preferences/indigo-dc)slam.your.url[/](https://<dockerhost>:<port>/)[rest/slam/preferences/indigo-dc](https://<dockerhost>:<port>/rest/slam/preferences/indigo-dc)

where indigo-dc is an organization configured in IAM.





