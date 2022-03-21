# NiceWeather
项目是对第一行代码（第三版）的java版改写及扩展：
# （1）网络层采用Retrofit+Rxjava实现，通过Rxjava的zip操作符将实时天气数据和未来天气数据进行合并后发射。另外，通过代理模式加入了网络隔离层，即使网络框架发生更改，也只需要在Application中修改一行代码即可。
# （2）dao层通过RxJava，在子线程读取本地数据，回调给主线程
# （3）项目后续想法：
    目前并没有存储多个天气数据的功能，可以加一个“+”按钮，跳转到另一个界面，显示出我们感兴趣的天气，这里可以通过JetPack中的Room来实现。
    项目没有使用DataBinding，因为在WeatherActivity的布局中，需要将所有include的子布局都填充完成后，才能可见，这个还不知道咋做。并且由于DataBinding的性能并不高，的确能减少findViewById这些胶水代码，但其在布局复杂的情况下反而不如findViewById
                  
