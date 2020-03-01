# ActivityResultHelper
This is a tools lib for getting ActivityResult more convenience

[![](https://img.shields.io/github/release/xiaojinzi123/ActivityResultHelper.svg?label=JitPack&color=%233fcd12)](https://jitpack.io/#xiaojinzi123/ActivityResultHelper)
[![](https://img.shields.io/github/release/xiaojinzi123/ActivityResultHelper.svg?label=JitPack-AndroidX&color=%233fcd12)](https://jitpack.io/#xiaojinzi123/ActivityResultHelper)
[![](https://img.shields.io/github/release/xiaojinzi123/ActivityResultHelper.svg?label=Release)](https://github.com/xiaojinzi123/ActivityResultHelper/releases)
[![](https://img.shields.io/github/tag/xiaojinzi123/ActivityResultHelper.svg?label=Tag)](https://github.com/xiaojinzi123/ActivityResultHelper/releases)
![](https://img.shields.io/github/last-commit/xiaojinzi123/ActivityResultHelper/develop.svg?label=Last%20Commit)
![](https://img.shields.io/github/repo-size/xiaojinzi123/ActivityResultHelper.svg)
![](https://img.shields.io/github/languages/code-size/xiaojinzi123/ActivityResultHelper.svg)
![](https://img.shields.io/github/license/xiaojinzi123/ActivityResultHelper.svg)

- support `Fragment`
- support `FragmentActivity`
- support all `Context` that is related to `FragmentActivity`, such as `Dialog#getContext()`

# Config

### Add it in your root build.gradle at the end of repositories

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

### Add the dependency

```
implementation 'com.github.xiaojinzi123:ActivityResultHelper:<version>'
```

`<version>` replace with the real version!

# Usage

## Get ActivityResult

```
ActivityResultHelper.with(fragmentActivity)
                .target(SecondAct.class)
                //.target(new Intent(this, SecondAct.class))
                .requestCodeRandom()
                .startForResult(new Callback<ActivityResult>() {
                    @Override
                    public void accept(@NonNull ActivityResult activityResult) {
                        // todo
                    }
                });
```

## Get Intent(without resultCode match)

```
ActivityResultHelper.with(fragment)
                .target(ThirdAct.class)
                .requestCodeRandom()
                .startForIntent(new Callback<Intent>() {
                    @Override
                    public void accept(@NonNull Intent intent) {
                        // todo
                    }
                });
```

## Get Intent(resultCode match)

```
ActivityResultHelper.with(context)
                .target(ThirdAct.class)
                .requestCodeRandom()
                .startForIntent(RESULT_OK, new Callback<Intent>() {
                    @Override
                    public void accept(@NonNull Intent intent) {
                        // todo
                    }
                });
```