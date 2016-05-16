package xyz.appint.union.dao.page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin
 */
public class NavPage<E> extends DefaultPage<E> {
    private static final int DEFAULT_NAVI_PAGE_SIZE = 10;
    private int naviPageSize = DEFAULT_NAVI_PAGE_SIZE; // 导航上最多可展示的页数 默认是10
    private int startPage; // 导航上开始页号码
    private int endPage; // 导航上结束页号码
    private String pageBar;


    public NavPage(int pageNumber, int pageSize, int totalCount, int naviPageSize) {
        this(pageNumber, pageSize, totalCount, new ArrayList<E>(0), naviPageSize);
    }

    public NavPage(int pageNumber, int pageSize, int totalCount, List<E> result, int naviPageSize) {

        super(pageNumber, pageSize, totalCount, result);
        this.naviPageSize = naviPageSize;
        if (this.naviPageSize == 0) {
            this.naviPageSize = DEFAULT_NAVI_PAGE_SIZE;
        }
        execute();
    }

    public NavPage(int pageNumber, int pageSize) {
        this(pageNumber, pageSize, -1, DEFAULT_NAVI_PAGE_SIZE);
    }

    public NavPage(PageRequest pageRequest, int totalCount, int naviPageSize) {
        this(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount, naviPageSize);
    }

    public NavPage(PageRequest pageRequest, int totalCount) {
        this(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount, DEFAULT_NAVI_PAGE_SIZE);
    }

    @Override
    public void setTotalCount(int totalCount) {
        super.setTotalCount(totalCount);
        execute();
    }

    protected void execute() {
        // 导航上开始页号码
        startPage = getPageNumber() % getNaviPageSize() == 0 ? ((getPageNumber() - 1) / getNaviPageSize())
                * getNaviPageSize() + 1
                : (getPageNumber() / getNaviPageSize()) * getNaviPageSize() + 1;

        // 导航上结束页号码
        endPage = startPage + getNaviPageSize() - 1 >= getPages() ? getPages()
                : startPage + getNaviPageSize() - 1;
    }


    public int getNaviPageSize() {
        if (this.naviPageSize == 0) {
            this.naviPageSize = DEFAULT_NAVI_PAGE_SIZE;
        }
        return naviPageSize;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

}
