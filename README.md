# clean-arch-mvvm / clean-arch-mvi  

**This project is self study and playing ONLY.**


## In this project, was implementation with

### Clean Architecture with MVI and/or MVVM ( base on activity level) 


| Layer | Description |
|----- | ------ |
| Presentation Layer | view, view model (MVVM & MVI)  |
| Domain Layer | Entities, usecase, Repository Interface |
| Data Layer | datamapping, Repository Impl.  |

### what is entities
Entities are business object of and application or a system

### Modularization patterns

This concerns modularization at the page level (List & Detail) 


### Unit test

Apply with jUnit5, mockk


### UI test

Espresso


### Library

| Library | Used For | Remark |
|----- | ------ | ------ |
| navigator | Switch two fragment  | ----- |
| databing | binding layout xml to class level | Also implemented jetpack compose later |
| hilt | Dependency injection `di`  | ------ |
| jacoco | Test report| ----|


### Project Structure

```
project
│   README.md
│   build.gradle    
│
└───app
│   │ HomeActivity - Fragment, JetpackMVVMMainActivity - MVVM, JetpackMVIMainActivity - MVI
│   │
└───base module
    │  stored base class concept
└───jetpack-design-lib
    │  stored common jetpack compose (like theme, dialog ...etc)
│   │
│   │        
└───asset-page <--module 1
    │   with Clean Architecture with MVI and fragment (base on activity)
    │   │
└───market-page <--module 2
    │   with Clean Architecture with MVVM and fragment (base on activity)
    │   │    
```


### UI test demo
https://github.com/jchodev/clean-arch-mvvm/assets/100594737/4915be8c-3501-4558-812f-13c09a47f58a

### jacocoReport command
./gradlew :asset-page:jacocoReport &  ./gradlew :market-page:jacocoReport  
![market-page](https://github.com/jchodev/clean-arch-mvvm/assets/100594737/51a8ca98-39f7-4a46-bc82-70bf070c0091)



## TODO
1. Replace MVVM to MVI
2. ...
