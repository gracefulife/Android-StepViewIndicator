# Android-StepViewIndicator
![Example image](./1.png) ![Example image](./2.png)

## Install

add your build.gradle
```
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}

dependencies {
  implementation 'com.github.KimKyung-man:Android-StepViewIndicator:v1.0.0'
}
```

  
## Usage
layout.xml
```
 <com.anton46.stepsview.StepsView
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/stepsView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        />
```        

SomeActivity.java
```
indicatorSteps.setLabels(new String[]{"test1", "test2", "test3", "test4"})
    .setProgressColorIndicator(ContextCompat.getColor(this, R.color.colorPrimary))
    .setBarColorIndicator(ContextCompat.getColor(this, R.color.colorAccent))
    .setCompletedPosition(1)
    .drawView();
```

## Improvements
the original version is https://github.com/anton46/Android-StepsView.

## License
Apache 2.0
