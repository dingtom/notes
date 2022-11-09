- [ [用keras实现GAN识别MNIST](https://github.com/dingtom/python/blob/master/GAN_MNIST_Keras%20(1).ipynb)](#head1)
- [ [Tensorflow实现GAN识别MNIST](https://github.com/dingtom/python/blob/master/GAN_MNIST_Tensorflow%20(1).ipynb)](#head2)
Goodfellow的Generative Adversarial Networks https://arxiv.org/abs/1406.2661
**本质上， GAN 目标是训练出一个好的生成模型，来模拟训练集中的数据。**不同的是， 一般的生成模型，必须先初始化一个“假设分布”，即后验分布，通过各种抽样方法抽样这个后验分布，就能知道这个分布与真实分布之间究竟有多大差异。这里的差异就要通过构造损失函数（ loss function ）来估算。知道了这个差异后，就能不断调优一开始的“假设分布”，从而不断逼近真实分布。限制玻尔兹曼机CRBM)就是这种生成模型的一种。然而，对抗网络可以学习自己的损失函数，无须精心设计和建构一个损失函数，却能达成无监督学习。
生成网络负责生成，辨别网络负责分辨生成的质量，然后不断的生成与辨别，最后达到效果。通过这种方式，损失函数被蕴含在判别器中了。我们不再需要思考损失函数应该如何设定

虽然，省去复杂的后验推断过程是GANs相对其他生成模型的优势。但是，早期的GANs 有许多问题，最主要的一项通病是GANs 不稳定“有时候它永远不会开始学习，或者生成我们认为合格的输出。DCGAN 方法对CNN 使用和修改的核心建议如下：
>- 在判别器中用带步长的卷积层（ strided convolutions ）取代的池化层（ pooling layers ） 。在生成器中用小步幅卷积C fractional strided convolutions ）取代的池化层（ pooling layers ） ，达到学习上采样的效果。
>- 在判别器和生成器中都采用Batch Normalization 批标准化。
>- 对于较深的网络，移除全连接层。
>- 在生成器中除了最后输出层，其他每一层输出使用ReLU 激活函数。在最后一层输出，可以使用Tanh 或Sigmoid 等两端饱和的激活函数。
>- 在判别器中的每一层使用LeakyReLU 激活函数。


以图片为例，在最理想的状态下，G可以生成足以“以假乱真”的图片G(z)。对于D来说，它难以判定G生成的图片究竟是不是真实的，因此D(G(z)) = 0.5。但是实际训练的时候这个状态一般是不可达的。上面的过程使用数学公式来表达：

$$
\min _{G} \max _{D} V(D, G)=\mathbb{E}_{\boldsymbol{x} \sim p_{\text {data }}(\boldsymbol{x})}[\log D(\boldsymbol{x})]+\mathbb{E}_{\boldsymbol{z} \sim p_{\boldsymbol{z}}(\boldsymbol{z})}[\log (1-D(G(\boldsymbol{z})))]
$$
分析这个公式：
- $x$表示真实输入
- $z$表示输入G网络的噪声
- $G(z)$表示G网络生成
- $D(x)$表示D网络判断真实图片是否真实的概率（因为x就是真实的，所以对于D来说，这个值越接近1越好）。
- $D(G(z))$是D网络判断G生成的图片的是否真实的概率
- G希望$D(G(z))$尽可能得大，这时$V(D, G)$会变小。因此我们看到式子的最前面的记号是$min_G$
- D希望$D(x)$越大，$D(G(z))$越小。这时$V(D,G)$会变大。因此式子对于D来说是求最大$max_D$

![](https://upload-images.jianshu.io/upload_images/18339009-75e67a26a46d195e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-8b283271ff4406f8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![](https://upload-images.jianshu.io/upload_images/18339009-830ecc6f5c3368d8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/18339009-f1d24ed94706abc3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)




需要注意的是，整个GAN的整个过程都是无监督的
这里，给的真图是没有经过人工标注的，而系统里的D并不知道来的图片是什么玩意儿，它只需要分辨真假。G也不知道自己生成的是什么，反正就是学真图片的样子骗D。
正由于GAN的无监督，在生成过程中，G就会按照自己的意思天马行空生成一些“诡异”的图片，可怕的是D还能给一个很高的分数。

### <span id="head1"> [用keras实现GAN识别MNIST](https://github.com/dingtom/python/blob/master/GAN_MNIST_Keras%20(1).ipynb)</span>
```
import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt
import sys
from tensorflow import keras
from tensorflow.keras.datasets import mnist
from tensorflow.keras.models import Model
from tensorflow.keras.optimizers import Adam
from tensorflow.keras.layers import *
import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt
import sys
from tensorflow import keras
from tensorflow.keras.datasets import mnist
from tensorflow.keras.models import Model
from tensorflow.keras.optimizers import Adam
from tensorflow.keras.layers import *
class GAN():
    def __init__(self):
        self.img_rows = 28
        self.img_cols = 28
        self.channels = 1
        self.img_shape = (self.img_rows, self.img_cols, self.channels)
        self.latent_dim = 100

        # -----------------------创建编译 discriminator 训练D----------------------------
        self.discriminator = self.build_discriminator()
        self.discriminator.compile(loss='binary_crossentropy',
                                   optimizer=Adam(0.0002, 0.5),
                                   metrics=['accuracy'])

        # ------------------------创建编译  DG 联合模型 训练G,inputs=z, outputs=label--------
        z = Input(shape=(self.latent_dim,))
        self.generator = self.build_generator()
        img = self.generator(z)
        
        # DG 联合模型在训练时discriminator不需训练
        # 只会关闭self.combined中discriminator的训练，之前的discriminator已经compile了，
        # 不影响discriminator单独训练。
        self.discriminator.trainable = False  
        label = self.discriminator(img)
        
        self.combined = Model(inputs=z, outputs=label)
        self.combined.summary()
        self.combined.compile(loss='binary_crossentropy',
                              optimizer=Adam(0.0002, 0.5))
        
    # 输入长为100的噪声，返回28*28图像
    def build_generator(self):
        noises = Input(shape=(self.latent_dim,))
        l = Dense(256, input_dim=self.latent_dim)(noises)
        l = LeakyReLU(alpha=0.2)(l)
        l = BatchNormalization(momentum=0.8)(l)
        l = Dense(512)(l)
        l = LeakyReLU(alpha=0.2)(l)
        l = BatchNormalization(momentum=0.8)(l)
        l = Dense(1024)(l)
        l = LeakyReLU(alpha=0.2)(l)
        l = BatchNormalization(momentum=0.8)(l)
        l = Dense(np.prod(self.img_shape), activation='tanh')(l)
        imgs = Reshape(self.img_shape)(l)
        return Model(inputs=noises, outputs=imgs)

    # 输入28*28图像，返回0/1标签
    def build_discriminator(self):
        imgs = Input(shape=self.img_shape)
        l = Flatten(input_shape=self.img_shape)(imgs)
        l = Dense(512)(l)
        l = LeakyReLU(alpha=0.2)(l)
        l = Dense(256)(l)
        l = LeakyReLU(alpha=0.2)(l)
        labels = Dense(1, activation='sigmoid')(l)
        return Model(inputs=imgs, outputs=labels)

    def train(self, epochs, batch_size=128, sample_interval=50):
        # 加载数据
        (x_train, y_train), (x_test, y_test) = mnist.load_data()
        # 归一化 -1 to 1
        x_train = x_train/127.5-1.
        # (60000, 28, 28)   --->  (60000, 28, 28, 1)
        x_train = np.expand_dims(x_train, axis=3)
        # 生成照片的标签真1假0
        real_label = np.ones((batch_size, 1))
        fake_label = np.zeros((batch_size, 1))

        for epoch in range(epochs):
            """
            GAN的训练在同一轮梯度反传的过程中可以细分为2步，先训练D在训练G；
            注意不是等所有的D训练好以后，才开始训练G，
            因为D的训练也需要上一轮梯度反传中G的输出值作为输入。
            """
            # ---------------------训练 Discriminator---------------------
            """
            当训练D的时候，上一轮G产生的新图片，和真实图片，直接拼接在一起，作为x。
            然后根据，按顺序摆放0和1假图对应0，真图对应1。
            然后就可以通过，x输入生成一个score（从0到1之间的数），通过score和y组成的损失函数，
            就可以进行梯度反传了。
            """
            # 随机选择一个batch 照片训练
            index = np.random.randint(0, x_train.shape[0], batch_size)
            real_imgs = x_train[index]
            # 生成一个batch假照片
            noise = np.random.normal(0, 1, (batch_size, self.latent_dim))
            fake_imgs = self.generator.predict(noise)
            # 开始训练
            d_loss_real = self.discriminator.train_on_batch(real_imgs, real_label)  
            # train_on_batch 返回compile里的 loss and metrics
            d_loss_fake = self.discriminator.train_on_batch(fake_imgs, fake_label)  
            # d_loss_fake [0.7735841, 0.0625]
            d_loss = 0.5 * np.add(d_loss_real, d_loss_fake)  
            # d_loss [0.7576531 0.21875  ]  D_loss Acc

            # ---------------------训练 DG模型---------------------
            """
            当训练G的时候， 需要把G和D当作一个整体。这个整体(下面简称DG系统)的输出仍然是score。
            输入一组随机向量，就可以在G生成一张图，通过D对生成的这张图进行打分， 这就是DG系统的前向过程。
            score=1就是DG系统需要优化的目标， score和y=1之间的差异可以组成损失函数，然后可以反向传播梯度。
            注意，这里的D的参数是不可训练的。这样就能保证G的训练是符合D的打分标准的。
            这就好比：如果你参加考试，你别指望能改变老师的评分标准"""
            noise = np.random.normal(0, 1, (batch_size, self.latent_dim))
            g_loss = self.combined.train_on_batch(noise, real_label)  
            # g_loss 0.63344437
            if epoch % 100 == 0:
                print ("epoch:{}， D_Acc:{}，D_loss:{}，G_loss:{}" .format(
                          epoch, 100*d_loss[1], d_loss[0], g_loss))
            if epoch % sample_interval == 0:
                self.show_images(epoch)
                
        self.combined.save('all_model.h5')
    def show_images(self, epoch):
        row, column = 5, 5
        noise = np.random.normal(0, 1, (row*column, self.latent_dim))
        fake_imgs = self.generator.predict(noise)
        # Rescale images 0 - 1
        fake_imgs = 0.5*fake_imgs+0.5
        fig, axs = plt.subplots(row, column)
        counter = 0
        for i in range(row):
            for j in range(column):
                axs[i,j].imshow(fake_imgs[counter, :, :, 0], cmap='gray')
                axs[i,j].axis('off')
                counter += 1
        # fig.savefig("images/{}.png".format(epoch))
        plt.show()
        plt.close()
        
if __name__ == '__main__':
    gan = GAN()
    gan.train(epochs=10000, batch_size=1024, sample_interval=1000)
```
### <span id="head2"> [Tensorflow实现GAN识别MNIST](https://github.com/dingtom/python/blob/master/GAN_MNIST_Tensorflow%20(1).ipynb)</span>
```
import tensorflow as tf
import numpy as np
import matplotlib.pyplot as plt
import sys
from tensorflow import keras
from tensorflow.keras.datasets import mnist
from tensorflow.keras.models import Model
from tensorflow.keras.optimizers import Adam
from tensorflow.keras.layers import *
class GAN():
    def __init__(self):
        self.img_rows = 28
        self.img_cols = 28
        self.channels = 1
        self.img_shape = (self.img_rows, self.img_cols, self.channels)
        self.latent_dim = 100

        # -----------------------创建编译 discriminator 训练D----------------------------
        self.discriminator = self.build_discriminator()
        self.discriminator.compile(loss='binary_crossentropy',
                                   optimizer=Adam(0.0002, 0.5),
                                   metrics=['accuracy'])

        # ------------------------创建编译  DG 联合模型 训练G,inputs=z, outputs=label--------
        z = Input(shape=(self.latent_dim,))
        self.generator = self.build_generator()
        img = self.generator(z)
        
        # DG 联合模型在训练时discriminator不需训练
        # 只会关闭self.combined中discriminator的训练，之前的discriminator已经compile了，
        # 不影响discriminator单独训练。
        self.discriminator.trainable = False  
        label = self.discriminator(img)
        
        self.combined = Model(inputs=z, outputs=label)
        self.combined.summary()
        self.combined.compile(loss='binary_crossentropy',
                              optimizer=Adam(0.0002, 0.5))
        
    # 输入长为100的噪声，返回28*28图像
    def build_generator(self):
        noises = Input(shape=(self.latent_dim,))
        l = Dense(256, input_dim=self.latent_dim)(noises)
        l = LeakyReLU(alpha=0.2)(l)
        l = BatchNormalization(momentum=0.8)(l)
        l = Dense(512)(l)
        l = LeakyReLU(alpha=0.2)(l)
        l = BatchNormalization(momentum=0.8)(l)
        l = Dense(1024)(l)
        l = LeakyReLU(alpha=0.2)(l)
        l = BatchNormalization(momentum=0.8)(l)
        l = Dense(np.prod(self.img_shape), activation='tanh')(l)
        imgs = Reshape(self.img_shape)(l)
        return Model(inputs=noises, outputs=imgs)

    # 输入28*28图像，返回0/1标签
    def build_discriminator(self):
        imgs = Input(shape=self.img_shape)
        l = Flatten(input_shape=self.img_shape)(imgs)
        l = Dense(512)(l)
        l = LeakyReLU(alpha=0.2)(l)
        l = Dense(256)(l)
        l = LeakyReLU(alpha=0.2)(l)
        labels = Dense(1, activation='sigmoid')(l)
        return Model(inputs=imgs, outputs=labels)

    def train(self, epochs, batch_size=128, sample_interval=50):
        # 加载数据
        (x_train, y_train), (x_test, y_test) = mnist.load_data()
        # 归一化 -1 to 1
        x_train = x_train/127.5-1.
        # (60000, 28, 28)   --->  (60000, 28, 28, 1)
        x_train = np.expand_dims(x_train, axis=3)
        # 生成照片的标签真1假0
        real_label = np.ones((batch_size, 1))
        fake_label = np.zeros((batch_size, 1))

        for epoch in range(epochs):
            """
            GAN的训练在同一轮梯度反传的过程中可以细分为2步，先训练D在训练G；
            注意不是等所有的D训练好以后，才开始训练G，
            因为D的训练也需要上一轮梯度反传中G的输出值作为输入。
            """
            # ---------------------训练 Discriminator---------------------
            """
            当训练D的时候，上一轮G产生的新图片，和真实图片，直接拼接在一起，作为x。
            然后根据，按顺序摆放0和1假图对应0，真图对应1。
            然后就可以通过，x输入生成一个score（从0到1之间的数），通过score和y组成的损失函数，
            就可以进行梯度反传了。
            """
            # 随机选择一个batch 照片训练
            index = np.random.randint(0, x_train.shape[0], batch_size)
            real_imgs = x_train[index]
            # 生成一个batch假照片
            noise = np.random.normal(0, 1, (batch_size, self.latent_dim))
            fake_imgs = self.generator.predict(noise)
            # 开始训练
            d_loss_real = self.discriminator.train_on_batch(real_imgs, real_label)  
            # train_on_batch 返回compile里的 loss and metrics
            d_loss_fake = self.discriminator.train_on_batch(fake_imgs, fake_label)  
            # d_loss_fake [0.7735841, 0.0625]
            d_loss = 0.5 * np.add(d_loss_real, d_loss_fake)  
            # d_loss [0.7576531 0.21875  ]  D_loss Acc

            # ---------------------训练 DG模型---------------------
            """
            当训练G的时候， 需要把G和D当作一个整体。这个整体(下面简称DG系统)的输出仍然是score。
            输入一组随机向量，就可以在G生成一张图，通过D对生成的这张图进行打分， 这就是DG系统的前向过程。
            score=1就是DG系统需要优化的目标， score和y=1之间的差异可以组成损失函数，然后可以反向传播梯度。
            注意，这里的D的参数是不可训练的。这样就能保证G的训练是符合D的打分标准的。
            这就好比：如果你参加考试，你别指望能改变老师的评分标准"""
            noise = np.random.normal(0, 1, (batch_size, self.latent_dim))
            g_loss = self.combined.train_on_batch(noise, real_label)  
            # g_loss 0.63344437
            if epoch % 100 == 0:
                print ("epoch:{}， D_Acc:{}，D_loss:{}，G_loss:{}" .format(
                          epoch, 100*d_loss[1], d_loss[0], g_loss))
            if epoch % sample_interval == 0:
                self.show_images(epoch)
                
        self.combined.save('all_model.h5')
    def show_images(self, epoch):
        row, column = 5, 5
        noise = np.random.normal(0, 1, (row*column, self.latent_dim))
        fake_imgs = self.generator.predict(noise)
        # Rescale images 0 - 1
        fake_imgs = 0.5*fake_imgs+0.5
        fig, axs = plt.subplots(row, column)
        counter = 0
        for i in range(row):
            for j in range(column):
                axs[i,j].imshow(fake_imgs[counter, :, :, 0], cmap='gray')
                axs[i,j].axis('off')
                counter += 1
        # fig.savefig("images/{}.png".format(epoch))
        plt.show()
        plt.close()
        
if __name__ == '__main__':
    gan = GAN()
    gan.train(epochs=10000, batch_size=1024, sample_interval=1000)
```
参考：
https://blog.csdn.net/leviopku/article/details/81292192












