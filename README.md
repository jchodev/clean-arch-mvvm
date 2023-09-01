# clean-arch-mvvm

**This project is self study and playing ONLY.**


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

Espresso


### Library

| Library | Used For | Remark |
|----- | ------ | ------ |
| navigator | Switch two fragment  | ----- |
| databing | binding layout xml to class level | Will be implement jetpack compose later |
| hilt | Dependency injection `di`  | ------ |
| jacoco | Test report| ----|


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


### UI test demo
https://github.com/jchodev/clean-arch-mvvm/assets/100594737/4915be8c-3501-4558-812f-13c09a47f58a

### jacocoReport command
./gradlew :asset-page:jacocoReport &  ./gradlew :market-page:jacocoReport  
![market-page](https://github.com/jchodev/clean-arch-mvvm/assets/100594737/51a8ca98-39f7-4a46-bc82-70bf070c0091)



## TODO
1. Add new activity / pages with jetpack compose
2. ...
