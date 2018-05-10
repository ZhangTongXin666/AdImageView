# 仿知乎创意广告
<br>
<img src="https://raw.githubusercontent.com/hongyangAndroid/demo_rvadimage/master/anim1.gif" width=320 height=500>
<br>
<h3>实现思路<h3><br>
1、监听列表滑动并计算图片Item的滑动距离
<pre><code>
        rvView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int fPos = linearLayoutManager.findFirstVisibleItemPosition(); //获得显示列表中第一个item的位置值
                int lPos = linearLayoutManager.findLastCompletelyVisibleItemPosition(); //获得显示列表中最后一个完全显示的Item位置值
               for (int i = fPos; i <= lPos; i++) {
                    View view = linearLayoutManager.findViewByPosition(i);
                    AdImageView adImageView = view.findViewById(R.id.ad_view);
                    if (i > 0 && i % 6 == 0) {  //根据条件判断当前Item是否是图片Item
                        //如果是图片Item则计算当前图片Item的滑动距离
                       int result = linearLayoutManager.getHeight() - view.getTop() - adImageView.getHeight();
                       adImageView.setDy(result);
                    }
                }
            }
        });
</code></pre>
<br>
2、自定义ImageView，为了不断设置画布的偏移。
public class AdImageView extends AppCompatImageView {
    private static final String TAG = "AdImageView";

    public AdImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private int mDy;
    private int mMinDy;

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        mMinDy = getHeight();
    }

    /**
     * 设置画布移动的距离
     * @param dy
     */
    public void setDy(int dy) {
        if (getDrawable() == null) {
            return;
        }
        mDy = dy;
        //检查画布移动的距离是否超过了图片的高度
        if (mDy > getDrawable().getBounds().height() - mMinDy) {
            mDy = getDrawable().getBounds().height() - mMinDy;
        }
        invalidate();
    }

    public int getDy() {
        return mDy;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(0, -getDy());// 移动画布，负数是向下移动。
        super.onDraw(canvas);
        canvas.restore();
    }
}
