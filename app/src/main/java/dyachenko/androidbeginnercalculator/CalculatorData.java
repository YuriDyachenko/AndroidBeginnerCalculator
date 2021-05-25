package dyachenko.androidbeginnercalculator;

import android.os.Parcel;
import android.os.Parcelable;

public class CalculatorData implements Parcelable {
    private final String leftOperand;
    private final String rightOperand;
    private final Operation operation;

    public CalculatorData(String leftOperand, String rightOperand, Operation operation) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operation = operation;
    }

    protected CalculatorData(Parcel in) {
        leftOperand = in.readString();
        rightOperand = in.readString();
        operation = (Operation) in.readSerializable();
    }

    public static final Creator<CalculatorData> CREATOR = new Creator<CalculatorData>() {
        @Override
        public CalculatorData createFromParcel(Parcel in) {
            return new CalculatorData(in);
        }

        @Override
        public CalculatorData[] newArray(int size) {
            return new CalculatorData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(leftOperand);
        dest.writeString(rightOperand);
        dest.writeSerializable(operation);
    }

    public String getLeftOperand() {
        return leftOperand;
    }

    public String getRightOperand() {
        return rightOperand;
    }

    public Operation getOperation() {
        return operation;
    }

}
