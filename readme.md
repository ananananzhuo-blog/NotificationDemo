关注公众号学习更多知识

![](https://files.mdnice.com/user/15648/404c2ab2-9a89-40cf-ba1c-02df017a4ae8.jpg)

## 概览

通知是 android 系统存在至今为止被变更最为频繁的 api 之一，android 4.1、4.4、5.0、7.0、8.0 都对通知做过比较大的改动。到了 8.0 通知功能趋于稳定，至今没有做过更大的改动。

对一个 api 进行如此大的照顾那么这必然是个非常重要的 api 了。那么就跟随我一起揭开通知一点都不神秘的面纱吧。

> 注：本文主要讲应用

## 通知使用

### 创建简单通知

我们使用 NotificationCompat 来创建通知，使用 NotificationCompat 可以兼容所有的系统版本，不需要我们去手动兼容版本。

创建通知分为两个步骤：

- 创建渠道
- 创建通知

#### 关于渠道

##### 创建渠道

```
notificationManager.createNotificationChannel(channel)
```

安卓 8.0 系统要求必须创建渠道才能展示通知，所以我们在 8.0 的系统版本中，必须添加创建渠道的方法。

创建渠道不一定非要在展示通知的时候做，同一个渠道只需要被创建一次即可（多次亦可）。我们可以在我们即将展示通知的时候创建，可以再应用启动的时候创建，也可以在 activity 中创建。总之渠道创建非常灵活

如果渠道已经存在我们仍然调用了创建渠道方法，那么什么也不会做，很安全

下面代码是我们创建渠道的完整代码：

```kotlin
private val channelName = "安安安安卓"
private val channelId = "channelId"
fun createNotificationChannel(context: Context): NotificationChannel? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = "渠道描述"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            return channel
        }
        return null
    }
```

##### 渠道重要性设置

**需要注意，渠道的优先级和通知的优先级是不同的，注意区分**

```
val importance = NotificationManager.IMPORTANCE_DEFAULT
val channel = NotificationChannel(channelId, channelName, importance)
```

上面的代码创建了通知的重要程度，我们需要说明一下 NotificationChannel 的第三个参数，也就是渠道的重要程度，这个设置不同的值，用户收到通知后手机的展示包括声音、震动、是否弹出都会不同，下面看一下参数的四种设置（**四个参数在不同手机的渠道展示不同**）：

- IMPORTANCE_HIGH 收到通知发出提示语，并且会浮动提示用户(小米手机表示紧急)
- IMPORTANCE_DEFAULT 收到通知发出提示语，不会浮动提示（小米手机表示高）
- IMPORTANCE_LOW 收到通知不会发出声音，状态栏有小图标展示（小米手机表示中）
- IMPORTANCE_MIN 根本看不到通知（所以你压根就别用就 ok 了），不过似乎可以用于禁用通知的场景（小米手机表示低）

##### 禁用某个渠道的通知方法

我们使用创建渠道的方式实现禁用通知，如下：

比如我们第一次创建渠道的时候代码如下：

```
val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance)
```

这行代码会创建一个有声音提示、横幅展示（google 文档管这个叫偷窥模式 😄）的渠道。

如果此时用户通过我们 app 内部的设置想不在收到我们这个渠道的通知，我们需要如下代码这样做：

```
val importance = NotificationManager.IMPORTANCE_MIN
            val channel = NotificationChannel(channelId, channelName, importance)
```

与上一处的代码的区别是把 IMPORTANCE_HIGH 改成了 IMPORTANCE_MIN，因此我们的渠道就变成了 低级别通知渠道，收到通知也无法展示，因此用户根本看不到通知，从而实现了通知禁用。

还有一点需要注意，我们可以通过代码将一个高优先级的渠道设置为低优先级渠道，但是无法将低优先级渠道设置为高优先级渠道。

#### 关于通知

##### 创建通知

通知大家都太熟悉，直接上代码，记得看注释

```
private val channelName = "安安安安卓"
    private val channelId = "channelId"
    fun showNotification(context: Context) {
        val notification = NotificationCompat.Builder(context, channelName)//这里的渠道名就是你自己想展示通知对应的渠道分组
            .setSmallIcon(R.drawable.apple)//设置状态栏展示的通知样式
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.apple))//设置通知中的图标样式
            .setContentTitle("公众号")//设置通知标题
            .setContentText("安安安安卓")//设置通知正文
            .setChannelId(channelId)//设置通知渠道，这个渠道id必须是和我们创建渠道时候的id对应
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()//设置通知优先级
        NotificationManagerCompat.from(context).notify(13, notification)
    }
```

**强调一下：展示通知之前一定要先创建渠道**

##### 通知中的优先级

设置方法：NotificationCompat.Builder.setPriority
**通知优先级极容易跟渠道优先级混淆，一定要注意区分**
通知优先级有以下几种：

- PRIORITY_DEFAULT = 0;默认优先级
- PRIORITY_LOW = -1; 低优先级
- PRIORITY_MIN = -2;最低优先级
- PRIORITY_HIGH = 1;高优先级
- PRIORITY_MAX = 2;最高优先级

这个参数主要是给我们的通知进行排序，重要的通知放在前面展示。这可以帮助我们第一时间找到最重要的通知进行处理，这很实用不是吗？

#### 展示效果

模拟器的展示效果：

![](https://files.mdnice.com/user/15648/93c2c1c2-4a05-417e-a793-a6e895bf12b0.png)

![](https://files.mdnice.com/user/15648/8c10d09e-67bf-4120-8bd4-28d266a00d2a.png)

### 创建展开式通知

#### 创建代码

我们可以再创建 NotificationCompat.Builder 的时候加上如下调用就可以展示展开式通知：

```
 .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("本文由 公众号 \"安安安安卓\"作者原创，禁抄袭\n 北国风光，" +
                            "千里冰封，万里雪飘，望长城内外，惟余莽莽，大河上下，顿失滔滔，山舞银蛇，原驰蜡象，欲与天公试比高。" +
                            "须晴日，看银装素裹，分外妖娆")
            )
```

通知默认是展开式的，长按通知可以在短文本和长文本之间来回切换

#### 展示效果

![](https://files.mdnice.com/user/15648/12494b8a-5054-4375-bb35-b676105d26f0.gif)

### 设置通知的点击事件

如下代码实现一个可点击的通知栏

```
  fun showNotification(context: Context) {
        val intent = Intent(context,OnlyShowActivity::class.java).apply {
            flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent=PendingIntent.getActivity(context,0,intent,0)
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentText("点击通知跳转的一个页面中")
            .setContentTitle("可点击通知")
            .setSmallIcon(R.drawable.apple)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.apple))
            .setAutoCancel(true)//设置点击了通知，则通知自动消失
            .setContentIntent(pendingIntent)
            .build()
        NotificationManagerCompat.from(context).notify(++count, notification)
    }
```

### 给通知栏设置按钮

我们可以通过 addAction 给通知设置 action，同时可以指定一个 PendingIntent。

```
fun showBtnNotification(context: Context) {
        val intent = Intent(context, OnlyShowActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.apple)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.apple))
            .setContentText("安安安安卓，北国风光，千里冰封，万里雪飘")
            .setContentTitle("按钮通知")
            .addAction(R.drawable.person, "李白", pendingIntent)
            .addAction(R.drawable.apple, "杜甫", pendingIntent)
            .addAction(R.drawable.apple, "王维", pendingIntent)
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(context).notify(++count, notification)
    }
```

### 设置进度条

```
 private val countdown = object : CountDownTimer(15 * 1000, 1000) {
        private val perdegree = 100 / 15
        var count = 0
        override fun onTick(millisUntilFinished: Long) {
            count++
            showNotification(count * perdegree)//更新进度
        }

        override fun onFinish() {
            showNotification(100)
            count = 0
        }
    }

    /**
     * 启动一个可动的进度条
     */
    fun start() {
        countdown.start()
    }

    private fun showNotification(progress: Int) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.apple)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.person))
            .setColor(Color.GREEN)
            .setContentTitle("这是个进度标题")

        NotificationManagerCompat.from(context).apply {
            builder.setProgress(100, progress, false)
            builder.setContentText("下载进度 $progress%")
            notify(count, builder.build())
        }
    }
```

效果图：

![](https://files.mdnice.com/user/15648/0bba1eda-886b-4e27-8d6a-9ab51d3f6dda.gif)

### 设置自定义通知

我们可以通过 RemoteViews 指定一个布局，通过 setCustomContentView 设置我们的自定义布局
代码：

```
 fun showNotification(context: Context){
        val remoteViews = RemoteViews(context.packageName, R.layout.item_notification)
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("这个通知的布局是自定义的")
            .setContentText("安安安安卓")
            .setSmallIcon(R.drawable.apple)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.person))
            .setCustomContentView(remoteViews)
            .build()
        NotificationManagerCompat.from(context).notify(count,notification)
    }
```

我们的 xml 代码预览图：

![](https://files.mdnice.com/user/15648/7ee597ea-fba3-4b7f-9c2b-d0e40ec1520f.png)

最终效果图：

![](https://files.mdnice.com/user/15648/de566617-a26b-4bd4-9615-49ad6a708efb.png)

## 其它的知识点

1. 从 android8.1 开始，应用一秒钟最多只能发出一次通知提示音，如果出现多条通知只有一条通知可以出发提示音
2. 创建通知的几种样式：NotificationCompat.BigPictureStyle、NotificationCompat.BigTextStyle、NotificationCompat.DecoratedCustomViewStyle
3. NotificationCompat.Builder.setGroup 方法可以创建一组通知
4. NotificationManager.getNotificationChannel()或 NotificationManager.getNotificationChannels()两个方法可以获取通知的渠道，通过获取到的渠道可以获取此渠道是否开启声音、渠道通知的重要级别。我们可以据此提示用户打开相应的设置，下面代码展示了打开通知渠道的方法：

```
  Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
    intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
    intent.putExtra(Settings.EXTRA_CHANNEL_ID, myNotificationChannel.getId());
    startActivity(intent);

```

5. 删除渠道的方法 deleteNotificationChannel()
6. 我们可以调用渠道的 NotificationChannel.setShowBadge(false)方法关闭桌面图标圆点。这个其实很有用，比如当你用通知展示下载进度条的时候这条通知明显是不需要展示圆点的，还有大部分的本地提醒类通知都不会希望显示圆点的，用这个方法正好
7. NotificationCompat.Builder.setNumber 方法可以设置桌面图标的红点数量
8. 通过 NotificationCompat.DecoratedCustomViewStyle 样式可以给内容区域创建自定义布局。样式就是通知展示图标在左，我们自定义的布局在右，不过这个感觉就没啥用了。
9. 自定义布局的通知也可以给内部的 view 添加点击跳转事件，实现方法如下代码：

```
 val remoteViews = RemoteViews(context.packageName, R.layout.item_notification)
        val intent = Intent(context,OnlyShowActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context,0,intent,0)
        remoteViews.setOnClickPendingIntent(R.id.iv_pendingintent_click,pendingIntent)
```
