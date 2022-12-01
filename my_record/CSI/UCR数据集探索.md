```
from keras.models import Model
from keras.utils import np_utils
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import keras
import os
from keras.callbacks import ReduceLROnPlateau


def readucr(filename):
    data = pd.read_csv(filename, sep='\t').values
    Y = data[:, 0]
    X = data[:, 1:]
    plt.plot(X[0, :])
    plt.plot(X[1, :])
    plt.show()
    return X, Y

def get_model(input_size, output_size):
    x = keras.layers.Input(input_size)
    #    drop_out = Dropout(0.2)(x)
    conv1 = keras.layers.Conv2D(128, 8, 1, border_mode='same')(x)
    conv1 = keras.layers.normalization.BatchNormalization()(conv1)
    conv1 = keras.layers.Activation('relu')(conv1)

    #    drop_out = Dropout(0.2)(conv1)
    conv2 = keras.layers.Conv2D(256, 5, 1, border_mode='same')(conv1)
    conv2 = keras.layers.normalization.BatchNormalization()(conv2)
    conv2 = keras.layers.Activation('relu')(conv2)

    #    drop_out = Dropout(0.2)(conv2)
    conv3 = keras.layers.Conv2D(128, 3, 1, border_mode='same')(conv2)
    conv3 = keras.layers.normalization.BatchNormalization()(conv3)
    conv3 = keras.layers.Activation('relu')(conv3)

    full = keras.layers.pooling.GlobalAveragePooling2D()(conv3)
    out = keras.layers.Dense(output_size, activation='softmax')(full)

    model = Model(input=x, output=out)

    optimizer = keras.optimizers.Adam()
    model.compile(loss='categorical_crossentropy', optimizer=optimizer, metrics=['accuracy'])
    return model


def train(each):
    fname = os.path.join(r'e:\Downloads\Compressed\UCRArchive_2018', each)
    train_path = os.path.join(fname, each + '_TRAIN.tsv')
    test_path = os.path.join(fname, each + '_TEST.tsv')
    x_train, y_train = readucr(train_path)
    x_test, y_test = readucr(test_path)
    nb_classes = len(np.unique(y_test))
    y_train = (y_train - y_train.min()) / (y_train.max() - y_train.min()) * (nb_classes - 1)
    y_test = (y_test - y_test.min()) / (y_test.max() - y_test.min()) * (nb_classes - 1)
    Y_train = np_utils.to_categorical(y_train, nb_classes)
    Y_test = np_utils.to_categorical(y_test, nb_classes)

    x_train_mean = x_train.mean()
    x_train_std = x_train.std()
    x_train = (x_train - x_train_mean) / (x_train_std)

    x_test = (x_test - x_train_mean) / (x_train_std)
    x_train = x_train.reshape(x_train.shape + (1, 1,))
    x_test = x_test.reshape(x_test.shape + (1, 1,))
    model = get_model(x_train.shape[1:], nb_classes)
    reduce_lr = ReduceLROnPlateau(monitor='loss', factor=0.5, patience=10, min_lr=0.0001)
    history = model.fit(x_train, Y_train, batch_size=8, nb_epoch=10, verbose=1, validation_data=(x_test, Y_test), callbacks=[reduce_lr])
    model.save('FCN_CBF_1500.h5')

    acc = history.history['acc']
    val_acc = history.history['val_acc']
    loss = history.history['loss']
    val_loss = history.history['val_loss']
    epochs = range(len(acc))
    plt.plot(epochs, acc, 'bo', label='Training acc')
    plt.plot(epochs, val_acc, 'b', label='Validation acc')
    plt.title('Training and validation accuracy')
    plt.legend()
    plt.figure()
    plt.plot(epochs, loss, 'bo', label='Training loss')
    plt.plot(epochs, val_loss, 'b', label='Validation loss')
    plt.title('Training and validation loss')
    plt.legend()
    plt.show()

flist = ['Car']
for each in flist:
      train(each)
```
