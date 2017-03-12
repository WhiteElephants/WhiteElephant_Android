/**
 * created by gordon rawe
 */

package rawe.gordon.com.pick.pick.base;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
