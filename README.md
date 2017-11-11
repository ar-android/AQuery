# AQuery

AQuery is like JQuery but it for android. AQuery allows the developer to write less and do more like Query View, Query Network and more.

## Getting Started

To use this library Add in you top build.gradle :

```gradle
allprojects {
    repositories {
      maven { url 'https://jitpack.io' }
    }
  }
```

Setup java 8 compiler for enable lambada expression in you app build.gradle :

```gradle
compileOptions {
  sourceCompatibility 1.8
  targetCompatibility 1.8
}
```

Add in you app build.gradle :

```gradle
    implementation 'com.github.ar-android:AQuery:v1.0.0'
```

## UseCase

As we have metion before AQuery is just like JQuery but for quering in android. Bellow example of use case.

Before using AQuery :

```java
Button login = findViewById(R.id.login);
login.setOnClickListener(new View.OnClickListenner(){
    @Override
    public void onClick(View v) {
      // Do stuff
    }
})
```

After using AQuery :
```java
aq.id(R.id.login).click(v -> {
  // Do stuff
});
```


## Built With

* [Okhttp](https://github.com/square/okhttp) - Networking Library.
* [Glide](https://github.com/bumptech/glide) - Image Loading and caching.
* [Gson](https://github.com/google/gson) - A Java serialization/deserialization

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags).

## Authors

* **Ahmad Rosid** - *Initial work* - [Ahmad Rosid](https://github.com/ar-android)

See also the list of [contributors](https://github.com/ar-android/AQuery/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
