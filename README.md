# Android-StepViewIndicator


## Usage

**Gradle**
```gradle
implementation 'com.github.KimKyung-man:Android-StepViewIndicator:v1.0.0'
````



```xml
 <com.anton46.stepsview.StepsView
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/stepsView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        />
```        

```java
indicatorSteps.setLabels(new String[]{"test1", "test2", "test3", "test4"})
    .setProgressColorIndicator(ContextCompat.getColor(this, R.color.colorPrimary))
    .setBarColorIndicator(ContextCompat.getColor(this, R.color.colorAccent))
    .setCompletedPosition(1)
    .drawView();
```


