# clean-arch-mvvm
This is self study project ONLY.


## In this project, was implementation with
`Clean Architecture with MVVM`
| Layer | Description |
|----- | ------ |
| Presentation Layer | view, view model (MVVM)  |
| Domain Layer | Entities, usecase, Repository Interface |
| Data Layer | datamapping, Repository Impl.  |


`Modularization patterns`

This concerns modularization at the page level (List & Detail) with studiny ONLY.

if any app only has two pages (a list page and a detail page), this pattern may not need to be applied in a real-world application.

`Unit test`

Apply with jUnit5

`UI test`

--TODO

`Library`

| Library | Used For | Remark |
|----- | ------ | ------ |
| navigator | Switch two fragment  | Will implement later |
| databing | binding layout xml to class level | Will be implement jetpack compose later |
| hilt | Dependency injection `di`  | ------ |


`Project Structure`

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
```

## TODO
1. Add detail page
2. add UI test
3. Add new pages with jetpack compose
