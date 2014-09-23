# AdsNative-Android-SDK #

## Installing AdsNative in your project ##

**1.** Download AdsNativeAndroidSDK.jar and copy it to your */libs/* folder inside your application project.


**For Android Studio users:**

Open the build.gradle file and edit your dependencies to include the new .jar file:

```
#!groovy
dependencies {
    compile files('libs/AdsNativeAndroidSDK.jar')
}
```

or 

Go to *File > Project structure*. Choose your module and add dependency to your project by *+ > File dependency* and choose *AdsNativeAndroidSDK.jar* from your */libs/* folder. 


** For Eclipse users:**

Go to *Project > Properties > Java Build Path > Libraries > Add external Jar* and choose *AdsNativeAndroidSDK.jar* from your */libs/* folder.



**2.** Add [Google Play Services](http://developer.android.com/google/play-services/setup.html) to your project.

**3.** Add permissions to your *AndroidManifest.xml* file:

```
#!xml

<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />   
<uses-permission android:name="android.permission.INTERNET" />
```

**4.** Add WebViewActivity to your *AndroidManifest.xml* file:

```
#!xml

<activity android:name="com.adsnative.android.sdk.WebViewActivity"
               android:configChanges="orientation|screenSize" />
```

**5.** Minimum Android SDK: Android 2.3 API (9)

## Using AdsNative APIs ##

### Integrating sponsored stories into ListView in ListActivity ###

It is as simple as it could be. All you have to do is to create an array of integers containing positions of sponsored stories in a ListView. Then create new *AdsNativeListAdapter*.

```
#!java

int[] sponsoredStoryPositions = {0, 4, 13, 69};
AdsNativeListAdapter adsNativeListAdapter = 
    new AdsNativeListAdapter(getBaseContext(), myAdapter, sponsoredStoryPositions, AD_UNIT_ID);
setListAdapter(adsNativeListAdapter);

//fetch and add SponsoredStories to the ListView
adsNativeListAdapter.loadSponsoredStories();  
```

Sponsored stories are going to be fetched and added to the ListView.

* myAdapter - developers' list adapter
* sponsoredStoryPositions - positions on the ListView where sponsored stories are going to be added.
* AD_UNIT_ID - AdsNative user ID

### Fetching single SponsoredStory ###

We use SponsoredStoryController to fetch and manage our SponsoredStory. 
Method getSponsoredStoryView( ... ) also sets time and impression logging listeners.


```
#!java

SponsoredStoryController sponsoredStoryController = new SponsoredStoryController(getBaseContext());

//fetches sponsored story
sponsoredStoryController.fetchSponsoredStory(AD_UNIT_ID); 

//listener when sponsored story is completely fetched 
sponsoredStoryController.setOnSponsoredStoryListener(new OnSponsoredStoryListener() {
            @Override
            public void onSponsoredStory(SponsoredStory sponsoredStory) {
                View view = sponsoredStoryController.getSponsoredStoryView(sponsoredStory);
            }

            @Override
            public void onFailure(FailureMessage failureMessage) {
                Log.e(ERROR_TAG, failureMessage.getMessage());
            }
});
```

* view - View object with default layout for SponsoredStory with layout_width = "match_parent" and layout_height="wrap_content"

### Examples ###

#### Put SponsoredStory at the bottom of the screen. ####


```
#!java

RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.my_activty_relative_layout);

sponsoredStoryController.setOnSponsoredStoryListener(new OnSponsoredStoryListener() {
            @Override
            public void onSponsoredStory(SponsoredStory sponsoredStory) {
                View view = sponsoredStoryController.getSponsoredStoryView(sponsoredStory);
                RelativeLayout.LayoutParams layoutParams = 
                              new RelativeLayout.LayoutParams(
                                             ViewGroup.LayoutParams.WRAP_CONTENT, 
                                             ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM); 
                relativeLayout.addView(view, layoutParams);
            }

            @Override
            public void onFailure(FailureMessage failureMessage) {
                Log.e(ERROR_TAG, failureMessage.getMessage());
            }
});
```

#### Attach data to developers custom Views that are inside specified layout. Provided layout has already been attached to some sort of a parent, i.e. in xml layout files. ####

Adding session ID tag to developers custom view is obligatory, in order to protect from render conflicts.

```
#!java

//textView is a child of relativeLayout
RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.my_sponsored_story_layout);
TextView textView = (TextView) findViewById(R.id.my_sponsored_story_text);

//Attach SponsoredStory to specified container (container has to be an instance of RelativeLayout) 
sponsoredStoryController.setOnSponsoredStoryListener(new OnSponsoredStoryListener() {
            @Override
            public void onSponsoredStory(SponsoredStory sponsoredStory) {
                textView.set("I'm SponsoredStory with title: " + sponsoredStory.getSponsoredStoryData().getTitle());
                relativeLayout.setTag(sponsoredStory.getSponsoredStoryData().getSessionId());
                sponsoredStoryController.getSponsoredStoryView(sponsoredStory, relativeLayout);
            }

            @Override
            public void onFailure(FailureMessage failureMessage) {
                Log.e(ERROR_TAG, failureMessage.getMessage());
            }
});
```

#### Attach data to developers custom Views that are inside specified Layout, that has NOT been attached to any parent. #####

Adding session ID tag to developers custom view is obligatory, in order to protect from render conflicts.

```
#!java

RelativeLayout mParent = (RelativeLayout) findViewById(R.id.my_activity_main_relative_layout);

//textView is a child of relativeLayout.
RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.my_sponsored_story_layout);
TextView textView = (TextView) findViewById(R.id.my_sponsored_story_text);

//Attach SponsoredStory to specified container (container has to be an instance of RelativeLayout) 
sponsoredStoryController.setOnSponsoredStoryListener(new OnSponsoredStoryListener() {
            @Override
            public void onSponsoredStory(SponsoredStory sponsoredStory) {
                textView.set("I'm SponsoredStory with title: " + sponsoredStory.getSponsoredStoryData().getTitle());
                relativeLayout.setTag(sponsoredStory.getSponsoredStoryData().getSessionId());
                sponsoredStoryController.getSponsoredStoryView(sponsoredStory, relativeLayout, mParent);
            }

            @Override
            public void onFailure(FailureMessage failureMessage) {
                Log.e(ERROR_TAG, failureMessage.getMessage());
            }
});
```

XML file:


```
#!xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/my_activity_main_relative_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <RelativeLayout
        android:id="@+id/my_sponsored_story_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" >

        <TextView
            android:id="@+id/my_sponsored_story_text"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

    </RelativeLayout>
</RelativeLayout>
```