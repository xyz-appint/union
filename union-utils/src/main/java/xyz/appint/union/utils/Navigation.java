package xyz.appint.union.utils;

import java.util.List;


public class Navigation<T> {

    private int pageSize; // 分页单位 ,每页显示的记录数量
    private int naviPageSize; // 导航上最多可展示的页数 默认是10
    private int currentPage; // 当前页
    private int allRows; // 数据表总记录数

    private int allPages; // 总页数

    private int startPage; // 导航上开始页号码
    private int endPage; // 导航上结束页号码
    private int prevPage; // 导航上上一页号码
    private int nextPage; // 导航上下一页号码

    private int startRow; // 提取得数据库的开始行

    private List<T> list;


    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Navigation(int allRows, int currentPage) {
        this.pageSize = 5;
        this.naviPageSize = 5;
        this.allRows = allRows;
        this.currentPage = currentPage;
        execute();
    }

    public Navigation(int pageSize, int allRows, int currentPage) {
        this.pageSize = pageSize;
        this.naviPageSize = 10;
        this.allRows = allRows;
        if (currentPage == 0) currentPage = 1;
        else this.currentPage = currentPage;
        execute();
    }

    public Navigation(int pageSize, int naviPageSize, int allRows, int currentPage) {

        this.pageSize = pageSize;
        this.naviPageSize = naviPageSize;
        this.allRows = allRows;
        this.currentPage = currentPage;

        execute();
    }

    private void execute() {
        // 计算总的页数，如果刚好是整页的情况，如allRows=10,20,30
        this.allPages = allRows / pageSize;
        // 如果不足一页要看成一页 ,如allRows=11,22,34 看成２ ３ ４页
        if (allRows % pageSize != 0)
            this.allPages++;

        // 处理当前页不在范围的情况
        this.currentPage = currentPage < 1 ? 1 : currentPage;
        this.currentPage = currentPage > allPages ? allPages : currentPage;

        // 没有下一页时 页号显示为0
        nextPage = (currentPage >= allPages ? 0 : currentPage + 1);
        // 没有上一页时 页号显示为0
        prevPage = (currentPage <= 1 ? 0 : currentPage - 1);

        // 导航上开始页号码
        startPage = currentPage % naviPageSize == 0 ? ((currentPage - 1) / naviPageSize)
                * naviPageSize + 1
                : (currentPage / naviPageSize) * naviPageSize + 1;

        // 导航上结束页号码
        endPage = startPage + naviPageSize - 1 >= allPages ? allPages
                : startPage + naviPageSize - 1;

        // 提取得数据库的开始行 数据库的行号从0开始
        startRow = (currentPage - 1) * pageSize;

        if (startRow < 0) {
            startRow = 0;
        }
    }


    public String getPageControl() {
        StringBuffer ctrl = new StringBuffer();
        if (getAllPages() > 1) {
            ctrl.append("<div class=\"page_nav\">");
            ctrl.append("共" + getAllRows() + "条  &nbsp; 共" + getAllPages() + "页&nbsp;");
            if (this.getCurrentPage() != 1) {
                ctrl.append("<a href=\"index.html\" >首页</a>&nbsp;&nbsp;");
                ctrl.append("<a href=\"$prevPageUrl\" >上一页</a>&nbsp;&nbsp;");
            }
            if (getPageSize() > 1) {
                for (int i = getStartPage(); i < getEndPage(); i++) {
                    if (i == this.getCurrentPage()) {
                        ctrl.append("<strong>" + i + "</strong>&nbsp;");
                    } else {
                        ctrl.append("<a href=\"$pageUrl\" >" + i + "</a>&nbsp;");
                    }
                }
            }
            if (this.getNextPage() > 0) {
                ctrl.append("<a href=\"${page.getNextPage()}.html\" >下一页</a>&nbsp;&nbsp;");
            }
            if (getCurrentPage() < getAllPages()) {
                ctrl.append("<a href=\"${page.getNextPage()}.html\" >尾页</a>&nbsp;&nbsp;");
            }
        }
        return ctrl.toString();
    }


    public int getPageSize() {
        return pageSize;
    }

    public int getNaviPageSize() {
        return naviPageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getAllRows() {
        return allRows;
    }

    public int getAllPages() {
        return allPages;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getStartRow() {
        return startRow;
    }


}