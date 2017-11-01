# ObjectAdapter
RecyclerView 升级Adapter
# Demo
Activity:
``` Java
presenterSelector = new ZhihuPresenterSelector(this);
objectAdapter = new ArrayObjectAdapter(presenterSelector);
for (IndexTypeEnum indexTypeEnum : data) {
    objectAdapter.add(indexTypeEnum);
}
recyclerView.setObjectAdapter(objectAdapter);
```
PresenterSelector:
``` Java
public class ZhihuPresenterSelector extends PresenterSelector {

    private final ArrayList<Presenter> mPresenters = new ArrayList<>();
    private FristPresenter fristPresenter;
    private SecondPresenter secondPresenter;
    private ThridPresenter thridPresenter;
    private ContainerPresenter containerPresenter;

    public ZhihuPresenterSelector(Context context) {
        fristPresenter = new FristPresenter(context);
        secondPresenter = new SecondPresenter(context);
        thridPresenter = new ThridPresenter(context);
        containerPresenter = new ContainerPresenter(context);

        mPresenters.add(fristPresenter);
        mPresenters.add(secondPresenter);
        mPresenters.add(thridPresenter);
        mPresenters.add(containerPresenter);
    }

    @Override
    public Presenter getPresenter(Object item) {
        IndexTypeEnum indexRow = (IndexTypeEnum) item;
        switch (indexRow) {
            case FRIST:
                return fristPresenter;
            case SECOND:
                return secondPresenter;
            case THRIED:
                return thridPresenter;
            case CONTAINER:
                return containerPresenter;
            default:
                return fristPresenter;
        }
    }

    @Override
    public Presenter[] getPresenters() {
        return mPresenters.toArray(new Presenter[mPresenters.size()]);
    }
}
```
FristPresenter:
``` Java
public class FristPresenter extends Presenter {

    private Context mContext;
    private LayoutInflater mInflater;

    public FristPresenter(Context context) {
        this.mContext = context;
        this.mInflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_frist, parent, false);
        return new InnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item, int position) {

    }

    @Override
    public void onUnBindViewHolder(ViewHolder viewHolder) {

    }

    protected class InnerViewHolder extends ViewHolder {

        public InnerViewHolder(View view) {
            super(view);
        }
    }
}

```
# Usage
``` Gradle
repositories {
    // ...
    maven { url "https://jitpack.io" }
}

dependencies {
    compile ''
}
```
# License
This project is licensed under the terms of the MIT license.
