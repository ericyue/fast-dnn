package suskun.nn;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * A generic class for carrying a float array and an index value attached to it.
 */
public class FloatData {

    private float[] data;
    public final int index;

    public static final FloatData EMPTY = new FloatData(new float[0], 0);

    public FloatData(float[] data, int index) {
        this.index = index;
        this.data = data;
    }

    public FloatData(float[] data) {
        this(data, 0);
    }

    public float[] getCopyOfData() {
        return data.clone();
    }


    /**
     * Create a copy of this and replaces the data with newData
     *
     * @param newData new Data to use in the copy.
     * @return a copy of this FloatData but with newData
     */
    public FloatData copy(float[] newData) {
        return new FloatData(newData, index);
    }

    /**
     * @param dataToAppend data to append
     * @return A new FloatData instance by appending the input data to this FrameData
     */
    public FloatData append(float[] dataToAppend) {
        float[] newData = Arrays.copyOf(data, data.length + dataToAppend.length);
        System.arraycopy(dataToAppend, 0, newData, data.length, dataToAppend.length);
        return new FloatData(newData, index);
    }

    /**
     * Appends the `dataToAppend` to data.
     */
    public void appendInPlace(float[] dataToAppend) {
        this.data = Arrays.copyOf(data, data.length + dataToAppend.length);
    }

    /**
     * Returns a new FloatData object. if newSize is more than the size, zeroes are appended.
     * if newSize is less than size, cropped copy is generated. In any case a new float array is generated.
     */
    public FloatData resize(int newSize) {
        float[] newData = Arrays.copyOf(data, newSize);
        return new FloatData(newData, index);
    }

    /**
     * Changes the size of the data array. A `newSize` is smaller than data size, data array will be truncated.
     */
    public void resizeInPlace(int newSize) {
        data = Arrays.copyOf(data, newSize);
    }


    /**
     * @param dataToPrepend data to append
     * @return A new FloatData instance by prepending the input data to this FrameData
     */
    public FloatData prepend(float[] dataToPrepend) {
        float[] newData = Arrays.copyOf(dataToPrepend, data.length + dataToPrepend.length);
        System.arraycopy(data, 0, newData, dataToPrepend.length, data.length);
        return new FloatData(newData, index);
    }

    /**
     * prepends the `dataToPrepend` to the data.
     */
    public void prependInPlace(float[] dataToPrepend) {
        float[] newData = Arrays.copyOf(dataToPrepend, data.length + dataToPrepend.length);
        System.arraycopy(data, 0, newData, dataToPrepend.length, data.length);
        data = newData;

    }

    /**
     * Create a copy of this.
     *
     * @return a copy of this FloatData but with newData
     */
    public FloatData copy() {
        return new FloatData(getCopyOfData(), index);
    }


    public int size() {
        return data.length;
    }

    public float[] getData() {
        return data;
    }

    public void serializeToBinaryStream(DataOutputStream dos) throws IOException {
        dos.writeInt(this.index);
        dos.writeInt(size());
        FeedForwardNetwork.serializeRaw(dos, this.data);
    }
}