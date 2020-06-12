package io.github.lamprose.bookshell.utils

import android.content.Context
import android.os.Environment
import java.io.File
import java.math.BigDecimal

class DataCleanManager {

    companion object{
        /**
         * 获取缓存大小
         * @param context
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun getTotalCacheSize(context: Context): String? {
            var cacheSize: Long = getFolderSize(context.cacheDir)
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                cacheSize += getFolderSize(context.externalCacheDir)
            }
            return getFormatSize(cacheSize.toDouble())
        }

        /**
         * 清除缓存
         * @param context
         */
        fun clearAllCache(context:Context) {
            deleteDir(context.cacheDir)
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                deleteDir(context.externalCacheDir)
            }
        }

        private fun deleteDir(dir:File?):Boolean {
            if (dir!!.isDirectory) {
                dir.listFiles().forEach {
                    if(!deleteDir(it))
                        return false
                }
            }
            return dir.delete()
        }
        // 获取文件大小
        //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
        //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
        //getCacheDir()方法用于获取/data/data//cache目录
        //getFilesDir()方法用于获取/data/data//files目录
        fun getFolderSize(file:File?):Long{
            var size = 0L
            try {
                if(file!!.isFile)
                    size += file.length()
                else if(file.isDirectory){
                    file.listFiles().forEach {
                        size+=getFolderSize(it)
                    }
                }
            } catch (e:java.lang.Exception) {
                e.printStackTrace()
            }
            return size
        }
        fun getFolderSize(filePath:String):String{
            val file=File(filePath)
            val size= getFolderSize(file)
            return getFormatSize(size.toDouble())
        }

        /**
         * 格式化单位
         * @param size
         * @return
         */
        fun getFormatSize(size:Double):String {
            val kiloByte:Double = size / 1024
            if (kiloByte < 1) {
//            return size + "Byte"
                return "0K"
            }

            val megaByte:Double = kiloByte / 1024
            if (megaByte < 1) {
                val result1 = BigDecimal(kiloByte.toString())
                return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K"
            }

            val gigaByte:Double = megaByte / 1024
            if (gigaByte < 1) {
                val result2 = BigDecimal(megaByte.toString())
                return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M"
            }

            val teraBytes:Double = gigaByte / 1024
            if (teraBytes < 1) {
                val result3 = BigDecimal(gigaByte.toString())
                return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB"
            }
            val result4 = BigDecimal(teraBytes)
            return result4.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "TB"
        }
    }
}