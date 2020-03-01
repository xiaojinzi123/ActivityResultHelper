# ActivityResultHelper
This is a tools lib for getting ActivityResult more convenience

# Config



# Useage

## Get ActivityResult

```
ActivityResultHelper.with(this)
                .target(SecondAct.class)
                //.target(new Intent(this, SecondAct.class))
                .requestCodeRandom()
                .startForResult(new Callback<ActivityResult>() {
                    @Override
                    public void accept(@NonNull ActivityResult activityResult) {
                        Toast.makeText(MainActivity.this, "start1", Toast.LENGTH_SHORT).show();
                    }
                });
```

## Get Intent(without resultCode match)

```
ActivityResultHelper.with(this)
                .target(ThirdAct.class)
                .requestCodeRandom()
                .startForIntent(new Callback<Intent>() {
                    @Override
                    public void accept(@NonNull Intent intent) {
                        
                    }
                });
```

## Get Intent(resultCode match)

```
ActivityResultHelper.with(this)
                .target(ThirdAct.class)
                .requestCodeRandom()
                .startForIntent(RESULT_OK, new Callback<Intent>() {
                    @Override
                    public void accept(@NonNull Intent intent) {
                        
                    }
                });
```