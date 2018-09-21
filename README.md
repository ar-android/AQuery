# AQuery

AQuery is like JQuery but it for android. AQuery allows the developer to write less and do more for managing element of android like Activity, View, Network, Caching and many more.

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

Add the dependency in you app build.gradle :

```gradle
implementation 'com.github.ar-android:AQuery:1.0.3'
```

## Use Case

As we have metion before AQuery is just like JQuery but for quering in android. Bellow example of use case.

Initializing AQuery :

```java
// AQuery in Activity
public class MainActivity extends AppCompatActivity{

    private AQuery aq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aq = new AQuery(this);
    }
}

// AQuery in Fragment
public class MainFragment extends Fragment{

    private AQuery aq;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        aq = new AQuery(getActivity());
    }
}

// AQuery in ViewHolder
public class ViewHolder extends RecyclerView.ViewHolder{

    private AQuery aq;

    public ViewHolder(View itemView) {
        super(itemView);
        aq = new AQuery(itemView.getContext(), itemView);
    }
}
```

Query Activity :
```java
// Fast intent
aq.open(HomeActivity.class);

// Custom intent
Intent intent = new Intent(this, HomeActivity.class);
intent.putExtra("id",1);
aq.open(intent);

// Intent transition from left and right
aq.openFromRight(HomeActivity.class);
aq.openFromLeft(HomeActivity.class);
aq.openFromRight(intent);
aq.openFromLeft(intent);

// Close activity transition
aq.closeToRight();
aq.closeToLeft();
```

Query View :
```java
// OnClickListener
aq.id(R.id.login).click(v -> {
  // Do stuff
});

// Check is text not empty
boolean valid = aq.id(R.id.input_email).isValid();

// Get text value of view is avaliable
String email = aq.id(R.id.input_email).text();

// Set Visibilty Gone
aq.id(R.id.input_email).hide();

// Set Visibilty Visible
aq.id(R.id.input_email).show();

// Cast view
DefaultTextField defaultTextField = aq.id(R.id.input_email).as(DefaultTextField.class);

// Active state View
aq.id(R.id.input_email).active();
aq.id(R.id.input_email).inActive();

// If you want rounded image
aq.id(R.id.image).image(user.getAvatar_url()).rounded();

// If you want set from drawable
aq.id(R.id.image).image(R.drawable.profile);
```

Query Toas :
```java
// Simple toas
aq.snack("Message");
aq.toast("Message");

```

Query Network :

```java
// Ajax get
aq.ajax("https://api.github.com/users/ar-android")
        .get()
        .showLoading()
        .toObject(GithubUsers.class, (user, error) -> {
            // Do stuff
        });

// Ajax POST form
Map<String, String> params = new HashMap<>();
params.put("email", aq.id(R.id.email).text());
params.put("password", aq.id(R.id.password).text());
aq.ajax("https://ocit-tutorial.herokuapp.com/index.php")
        .postForm(params)
        .showLoading()
        .response((response, error) -> {
            if (response != null){
                aq.openFromRight(MainActivity.class);
            }
        });

```

Query Shared Preferences :

```java
// Save string to shared preferences
aq.saveString("token", response.getData().getToken());

// Grab string from shared preferences
String token = aq.grabString("token");
```

Query SQLite :

```java
//Storing data to SQLite
Map<String, Object> data = new HashMap<>();
data.put("nama", "Putri Nuraini");
data.put("email", "alahmadrosid@gmail.com");
aq.sql().table("user").insert(data);

// Get All row from table
List<Map<String, String>> user = aq.sql().table("user").all();

// Update row from table by id
boolean update = aq.sql().table("user").update(3, data);

// Get row table by id
Map<String, String> user = aq.sql().table("user").get(3);

// Get table by first row
Map<String, String> user = aq.sql().table("user").first();

// Get table by last row
Map<String, String> user = aq.sql().table("user").last();

// Delete table by id
boolean delete = aq.sql().table("user").delete(2);

// Clear database sqlite
aq.sql().clearDb();
```
And AQuery have a lot of API check it on [Documentation](https://ar-android.github.io/AQuery)

## Built With

* [Okhttp](https://github.com/square/okhttp) - Networking Library.
* [Glide](https://github.com/bumptech/glide) - Image Loading and caching.
* [Gson](https://github.com/google/gson) - A Java serialization/deserialization for JSON.

## Authors

* **Ahmad Rosid** - *Initial work* - [Ahmad Rosid](https://github.com/ar-android)

See also the list of [contributors](https://github.com/ar-android/AQuery/contributors) who participated in this project.

# License

    Copyright 2017 Ahmad Rosid

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.