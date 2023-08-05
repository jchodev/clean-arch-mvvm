# clean-arch-mvvm

**This is self study and playing roject ONLY.**


## In this project, was implementation with

### Clean Architecture with MVVM


| Layer | Description |
|----- | ------ |
| Presentation Layer | view, view model (MVVM)  |
| Domain Layer | Entities, usecase, Repository Interface |
| Data Layer | datamapping, Repository Impl.  |


### Modularization patterns

This concerns modularization at the page level (List & Detail) 


### Unit test

Apply with jUnit5


### UI test

--TODO


### Library

| Library | Used For | Remark |
|----- | ------ | ------ |
| navigator | Switch two fragment  | ----- |
| databing | binding layout xml to class level | Will be implement jetpack compose later |
| hilt | Dependency injection `di`  | ------ |


### Project Structure

```
project
│   README.md
│   build.gradle    
│
└───app
│   │
│   │
└───base module
    │  stored base class concept
│   │
│   │        
└───asset-page <--module 1
    │   with Clean Architecture with MVVM
    │   │
└───market-page <--module 2
    │   with Clean Architecture with MVVM
    │   │    
```

## TODO
1. add UI test (Preparing ... still cannot run)
2. Add jacocoReport
3. Add new pages with jetpack compose
4. ...
