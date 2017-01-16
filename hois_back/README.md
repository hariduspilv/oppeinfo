# Backend via Spring Boot
* version 1.4.3
* Query criterias are made using Specification API
* packages hierarchy
  * ee.hitsa.ois
  * ee.hitsa.ois.domain
  * ee.hitsa.ois.enums
  * ee.hitsa.ois.repository
  * ee.hitsa.ois.repository.specification
  * ee.hitsa.ois.service
  * ee.hitsa.ois.web
  * ee.hitsa.ois.web.commandobject
  * ee.hitsa.ois.web.dao

## TODO
1. Interface based projections still select all columns from database. May be should search for alternative approach (DTO)
  * Seperate repository
  * CriteriaBuilder constructor