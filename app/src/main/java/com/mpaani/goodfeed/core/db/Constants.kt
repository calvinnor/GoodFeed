@file: JvmName("Constants")

package com.mpaani.goodfeed.core.db

const val DATABASE_NAME = "goodfeed.db"
const val DATABASE_VERSION = 1
const val DATABASE_IN_MEMORY_TEST = false

/** Comment Table **/
const val COMMENT_TABLE_NAME = "comments"
const val COMMENT_POST_ID = "postId"
const val COMMENT_ID = "id"
const val COMMENT_NAME = "name"
const val COMMENT_EMAIL = "email"
const val COMMENT_BODY = "body"

/** User Table **/
const val USER_TABLE_NAME = "users"
const val USER_ID = "userId"
const val USER_SIMPLE_NAME = "name"
const val USER_USERNAME = "username"
const val USER_EMAIL = "email"
const val USER_PHONE = "phone"
const val USER_WEBSITE = "website"
const val USER_COMPANY_NAME = "companyName"
const val USER_COMPANY_CATCHPHRASE = "companyCatchPhrase"
const val USER_COMPANY_BS = "companyBs"
const val USER_ADDRESS_STREET = "addressStreet"
const val USER_ADDRESS_CITY = "addressCity"
const val USER_ADDRESS_SUITE = "addressSuite"
const val USER_ADDRESS_ZIPCODE = "addressZipCode"
const val USER_ADDRESS_GEO_LAT = "addressGeoLat"
const val USER_ADDRESS_GEO_LNG = "addressGeoLng"

/** Post Table **/
const val POST_TABLE_NAME = "posts"
const val POST_ID = "id"
const val POST_USER_ID = "userId"
const val POST_TITLE = "title"
const val POST_BODY = "body"
