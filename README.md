# MapController

能在Android GoogleMap元件上獲取地址、經緯度


### Features
* 拖移Map獲取地址、經緯度
* 點擊Map獲取地址、經緯度
* 搜尋地址、經緯度能跳轉Map上的位置



# Usage

### 宣告MapController

給予GooglMap、TextView元件，TextView元件用於顯示地址非必要
```java
MapController mapController = new MapController(getApplicationContext(),mMap,show);

//可關閉拖移GoogleMap就獲取地址、經緯度，能改善數據流量
mapController.isMoveGet(true);
```


### 使用Lisitener

```java
implements MapController.onGeoLoadedLisitener
mapController.setOnGeoLoadedLisitener(this);

//當獲取地址或經緯度時geo能直接拿取資料
@Override
public void onGeoLoaded(GeoInfo geo) {
    Toast.makeText(getApplicationContext(), geo.name, Toast.LENGTH_SHORT).show();
}
```
