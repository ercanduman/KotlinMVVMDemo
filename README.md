
# KotlinMVVMDemo
This is a sample demo android application that I have created to apply MVVM and new android architecture components.

I have followed below diagram as reference which shows basic form of MVVM architecture.

![mvvm-architecture](https://user-images.githubusercontent.com/11629459/49515908-3e1c3e80-f8a9-11e8-8360-2a3a4d2e6227.png)

You can find all MVVM architecture codes for Activity, Fragment, ViewModel, ViewModelFactory, LiveData, Repository, Dao, Entity, Room database, Retrofit and Glide library, etc.

## And also the following features (code samples) will be presented:

* Applying Retrofit library to get data from WebService
* Creating MainActivity as a host activity and using fragments for all other views
* Using GSON library to convert JSON data into Kotlin data classes
* Creating Api interface for retrofit requests
* Applying Data Binding to layout xml files 
* Repository objects to make network calls and/or retrieving data from local database
* Room Database, Entity and Dao objects creation
* Writing SQL queries to retrieve data from Room database based on different conditions
* Creating AppDatabase and applying Singleton design pattern
* Using Coroutines and suspend functions for long running background operations
* Creating Coroutines extension functions for different operations (IO, Main, etc.)
* Creating ViewModelFactory objects to provide ViewModels
* Using Groupie library to list items on RecyclerView
* Using Glide library to download photo from url and display images in Adapter
* Creating interface named ProcessListener to listen process steps
* Handling Api and Network Exceptions (NoNetworkException in Exceptions.kt) in a separate object (SafeApiRequest.kt)
* Using SharedPreferences to store simple data locally (PreferenceProvider.kt)
* Creating ViewAdapter to adapt every single item of photo to RecyclerView
* Creating Kotlin Extension functions (ViewUtils.kt) i.e.: Context.toast(message: String), Context.logd(debugMessage: String), etc.
* Showing network responses on Snackbar (with extension function)
* Applying Android Navigation Architecture and store all fragments in navigation component.
* Passing bundle data between Navigation Architecture fragments 
* Adding Item click listener to Navigation Architecture fragments
* Applying Kotlin Dependency Injection design pattern with KodeIn library

### Application, Api and APK file
MVVM demo application uses data from [JsonPlaceHolder](https://jsonplaceholder.typicode.com/) Api.
Application contains three different fragments called Album Fragment, Photo Fragment and Gallery Fragments. 
 
![1_home drawer screen](https://user-images.githubusercontent.com/11629459/64870069-40d36d80-d64b-11e9-8c04-7ee752855346.png)


Data for all albums are retrieved from JsonPlaceHolder api with this url: https://jsonplaceholder.typicode.com/albums and all albums are listed in Albums Fragment as shown in below image.

![2_albums](https://user-images.githubusercontent.com/11629459/64870066-40d36d80-d64b-11e9-82fd-6004c0fc1d92.png)

Every album contains multiple photos so if any album is clicked then Photos Fragment started and all photos that available in current album retrieved from this url: https://jsonplaceholder.typicode.com/photos?albumId=1 
Photos are inserted and saved to Room database and then listed as shown below. 
 
![2_1_album photos](https://user-images.githubusercontent.com/11629459/64870064-40d36d80-d64b-11e9-9582-c9f07c069489.png)

As displayed in above image Album id: 3 has 50 images.

MVVM demo application does not make any network call for Gallery Fragment. Because in Gallery fragment already downloaded photos will be displayed. As shown in below diagram's Snackbar there are already 700 images downloaded.

![3_All photos downloaded so far](https://user-images.githubusercontent.com/11629459/64870068-40d36d80-d64b-11e9-9426-31803b57cfef.png)

If you would like to try the application, you can download APK file from this link:
[MVVM_DEMO_app_debug_apk.zip](https://github.com/ercanduman/KotlinMVVMDemo/files/3610463/MVVM_DEMO_app_debug_apk.zip)

#### Helpful links:

- [x]  [MVVM Architecture Components](https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#0)
- [x] [Reference](https://developer.android.com/reference)

Happy coding! :+1: :1st_place_medal:
