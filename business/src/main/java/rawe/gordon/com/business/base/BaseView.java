/**
 * created by gordon rawe
 */

package rawe.gordon.com.business.base;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
