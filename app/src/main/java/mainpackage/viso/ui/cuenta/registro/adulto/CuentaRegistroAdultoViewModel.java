package mainpackage.viso.ui.cuenta.registro.adulto;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class CuentaRegistroAdultoViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public CuentaRegistroAdultoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is informacion fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
