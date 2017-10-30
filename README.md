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
