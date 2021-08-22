package com.skillbox.skillbox.contentprovider.custom_content_provider

import android.content.*
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import com.skillbox.skillbox.contentprovider.BuildConfig.APPLICATION_ID
import com.squareup.moshi.Moshi

class CustomContentProvider : ContentProvider() {
    //    создаем late init SharedPreferences для пользователей и юзеров, чтобы удобно к ним обращться из любой функции
    private lateinit var userPrefs: SharedPreferences
    private lateinit var coursesPrefs: SharedPreferences

    //    создаем адаптеры моши для пользователей и курсов, так как данные у нас хранятся в формате Json
    private val userAdapter = Moshi.Builder().build().adapter(User::class.java)
    private val courseAdapter = Moshi.Builder().build().adapter(Course::class.java)

    //    создаем uriMatcher для нашего провайдера для определения типа пришедшего к нам uri, добавляем в него необходимые нам варианты
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITIES, PATH_USERS, TYPE_USERS)
        addURI(AUTHORITIES, PATH_COURSES, TYPE_COURSES)
        addURI(AUTHORITIES, "$PATH_USERS/#", TYPE_USER_ID)
        addURI(AUTHORITIES, "$PATH_COURSES/#", TYPE_COURSE_ID)
    }

    override fun onCreate(): Boolean {
//        инициализируем sharedPrefs
        userPrefs = context!!.getSharedPreferences("user_shared_prefs", Context.MODE_PRIVATE)
        coursesPrefs = context!!.getSharedPreferences("course_shared_prefs", Context.MODE_PRIVATE)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        Log.i("course", uri.toString())
//        в зависимости от пришедшего к нам uri выполняем различные методы получения данных для заказчика
        return when (uriMatcher.match(uri)) {
            TYPE_USERS -> getAllUsersCursor()
            TYPE_COURSES -> getAllCoursesCursor()
            TYPE_COURSE_ID -> getCourseById(uri)
            else -> null
        }
    }

    //    получаем курсор списка всех пользователей
    private fun getAllUsersCursor(): Cursor {
//    получаем список всех пользователей из UserSharedPrefs и преобразуем их из Json-строки в объекты
        val allUsers = userPrefs.all.mapNotNull {
            val userJsonString = it.value as String
            userAdapter.fromJson(userJsonString)
        }
//    создаем курсор с колонками параметров пользователя
        val cursor = MatrixCursor(arrayOf(COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_AGE))
//    заполняем таблицу курсора из списка пользователей
        allUsers.forEach {
            cursor.newRow()
                .add(it.id)
                .add(it.name)
                .add(it.age)
        }
//    возвращаем курсор
        return cursor
    }

    //    получаем курсор списка курсов
    private fun getAllCoursesCursor(): Cursor {
//    получаем список всех курсов из CoursesSharedPrefs и преобразуем их из Json-строки в объекты
        val allCourses = coursesPrefs.all.mapNotNull {
            val courseJsonString = it.value as String
            courseAdapter.fromJson(courseJsonString)
        }
        Log.i("Course", allCourses.toString())
//    создаем курсор с колонками параметров курса
        val cursor = MatrixCursor(arrayOf(COLUMN_COURSE_ID, COLUMN_COURSE_TITLE))
//    заполняем таблицу курсора из списка курсов
        allCourses.forEach {
            cursor.newRow()
                .add(it.id)
                .add(it.title)
        }
//    возвращаем курсор
        return cursor
    }

    //    получаем курсор определенного курса
    private fun getCourseById(uri: Uri): Cursor? {
        Log.i("Course", uri.toString())
//    получаем id курса
        val id = uri.lastPathSegment?.toLongOrNull()?.toString() ?: return null
//    если такой id отсутствует в coursePrefs то возвращаем null
        return if (coursesPrefs.contains(id)) {
//            получаем Json-строку курса
            val courseJsonString = coursesPrefs.getString(id, "")
//            получаем объект курса
            val course = courseAdapter.fromJson(courseJsonString!!)
            Log.i("Course", course.toString())
//            создаем курсор с колонками параметров курса
            val cursor = MatrixCursor(arrayOf(COLUMN_COURSE_ID, COLUMN_COURSE_TITLE))
//            заполняем курсор
            course.also {
                cursor.newRow()
                    .add(it?.id)
                    .add(it?.title)
            }
//            возвращаем курсор
            cursor
        } else {
            null
        }
    }

    //    определение типа объекта из пришедшего uri
    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        values ?: return null
        return when (uriMatcher.match(uri)) {
//    в зависимости от пришедшего типа uri добавляем определенный объект
            TYPE_USERS -> saveUser(values)
            TYPE_COURSES -> saveCourse(values)
            else -> null
        }
    }

    //    сохраняем нового пользователя
    private fun saveUser(contentValues: ContentValues): Uri? {
//    из пришедших contentValues получаем имя, id и возраст пользователя
        val id = contentValues.getAsLong(COLUMN_USER_ID)
        val name = contentValues.getAsString(COLUMN_USER_NAME)
        val age = contentValues.getAsInteger(COLUMN_USER_AGE)
//    создаем объект пользователя
        val user = User(id, name, age)
//    добавляем пользователя в userPrefs
        userPrefs.edit()
            .putString(id.toString(), userAdapter.toJson(user))
            .apply()
//    возвращаем uri добавленного пользователя
        return Uri.parse("content://$AUTHORITIES/$PATH_USERS/$id")
    }

    //    сохраняем новый курс
    private fun saveCourse(contentValues: ContentValues): Uri? {
        Log.i("saving", "${Thread.currentThread()}")
//    из пришедших contentValues получаем название и id курса
        val id = contentValues.getAsLong(COLUMN_COURSE_ID) ?: return null
        val title = contentValues.getAsString(COLUMN_COURSE_TITLE) ?: return null
//    получаем объект курса
        val course = Course(id, title)
        Log.i("Course", course.toString())
//    добавляем курс в coursesPrefs
        coursesPrefs.edit()
            .putString(id.toString(), courseAdapter.toJson(course))
            .apply()
//    возвращаем uri добавленного курса
        val q = Uri.parse("content://$AUTHORITIES/$PATH_COURSES/$id")
        Log.i("course", q.toString())
        return Uri.parse("content://$AUTHORITIES/$PATH_COURSES/$id")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.i("course", uri.toString())
        return when (uriMatcher.match(uri)) {
//    в зависимости от пришедшего типа uri выполняем определенный метод удаления юзера/курса(-ов)
            TYPE_USER_ID -> deleteUser(uri)
            TYPE_COURSES -> deleteAllCourses()
            TYPE_COURSE_ID -> deleteCourseById(uri)
            else -> 0
        }
    }

    //    удаление пользователя
    private fun deleteUser(uri: Uri): Int {
//    получаем ID пользователя
        val userId = uri.lastPathSegment?.toLongOrNull()?.toString() ?: return 0
//    в зависимости от успешности операции удаления возращаем ответ
        return if (userPrefs.contains(userId)) {
            userPrefs.edit()
                .remove(userId)
                .apply()
            1
        } else {
            0
        }
    }

    //    удаление курса
    private fun deleteCourseById(uri: Uri): Int {
//    получаем ID курса
        val courseId = uri.lastPathSegment?.toLongOrNull()?.toString() ?: return 0
//    в зависимости от успешности операции удаления возращаем ответ
        return if (coursesPrefs.contains(courseId)) {
            coursesPrefs.edit()
                .remove(courseId)
                .apply()
            1
        } else {
            0
        }
    }

    //    удаляем все курсы сразу
    private fun deleteAllCourses(): Int {
        coursesPrefs.edit()
            .clear()
            .apply()
        return 1
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        Log.i("course", uri.toString())
//    если изменений нет, то сразу возвращаем 0
        values ?: return 0
//    в зависимости от пришедшего типа uri обновляем определенный элемент
        return when (uriMatcher.match(uri)) {
            TYPE_USER_ID -> updateUser(uri, values)
            TYPE_COURSE_ID -> updateCourseById(uri, values)
            else -> 0
        }
    }

    //    обновляем данные пользователя
    private fun updateUser(uri: Uri, contentValues: ContentValues): Int {
//    получаем id пользователя
        val userId = uri.lastPathSegment?.toLongOrNull()?.toString() ?: return 0
//    в зависимости от успешности операции изменения возращаем ответ
        return if (userPrefs.contains(userId)) {
            saveUser(contentValues)
            1
        } else {
            0
        }
    }

    //    обновляем данные курса
    private fun updateCourseById(uri: Uri, contentValues: ContentValues): Int {
//    получаем id курса
        val courseId = uri.lastPathSegment?.toLongOrNull()?.toString() ?: return 0
        contentValues.put(COLUMN_COURSE_ID, courseId)
        Log.i("Course", "update $contentValues")
//    в зависимости от успешности операции изменения возращаем ответ
        return if (coursesPrefs.contains(courseId)) {
            saveCourse(contentValues)
            1
        } else {
            0
        }
    }

    //    создаем константы для контент провайдера
    companion object {
        //    константы для uri
        private const val AUTHORITIES = "${APPLICATION_ID}.provider"
        private const val PATH_USERS = "users"
        private const val PATH_COURSES = "courses"

        private const val TYPE_USERS = 1
        private const val TYPE_COURSES = 2
        private const val TYPE_USER_ID = 3
        private const val TYPE_COURSE_ID = 4

        //     константы для колонок курсора пользователя и курсов
        private const val COLUMN_USER_ID = "id"
        private const val COLUMN_USER_NAME = "name"
        private const val COLUMN_USER_AGE = "age"

        private const val COLUMN_COURSE_ID = "id"
        private const val COLUMN_COURSE_TITLE = "title"
    }
}