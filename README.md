# 自己实现类继承 IHttpProcessor，并且初始化
public class xUtilsProcessor implements IHttpProcessor {

    public xUtilsProcessor(Application context) {
     
        HttpHelper.init(this);
    }

    @Override
    public void post(String url, Map<String, Object> parmas, final ICallback iCallback) {
        //正在实现网络请求的框架
    }
}

在自己的appcaction里面初始化
 new xUtilsProcessor(getApplication());


在Activity里面 调用HttpHelper.obtion().post(url, null, new HttpCallback<String>() {

            @Override
            public void onSucess(String result) {
              
            }

            @Override
            public void onFailure() {

            }
        });
