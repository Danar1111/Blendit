package com.android.blendit.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseImageCarousel(

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("total_pages")
	val totalPages: Int,

	@field:SerializedName("results")
	val results: List<ResultsItemUnsplash>
)

data class Source(

	@field:SerializedName("meta_description")
	val metaDescription: String,

	@field:SerializedName("ancestry")
	val ancestry: Ancestry,

	@field:SerializedName("cover_photo")
	val coverPhoto: CoverPhoto,

	@field:SerializedName("meta_title")
	val metaTitle: String,

	@field:SerializedName("subtitle")
	val subtitle: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("title")
	val title: String
)

data class Type(

	@field:SerializedName("pretty_slug")
	val prettySlug: String,

	@field:SerializedName("slug")
	val slug: String
)

data class CoverPhoto(

	@field:SerializedName("topic_submissions")
	val topicSubmissions: TopicSubmissions,

	@field:SerializedName("current_user_collections")
	val currentUserCollections: List<Any>,

	@field:SerializedName("color")
	val color: String,

	@field:SerializedName("sponsorship")
	val sponsorship: Any,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("description")
	val description: Any,

	@field:SerializedName("liked_by_user")
	val likedByUser: Boolean,

	@field:SerializedName("plus")
	val plus: Boolean,

	@field:SerializedName("urls")
	val urls: Urls,

	@field:SerializedName("alternative_slugs")
	val alternativeSlugs: AlternativeSlugs,

	@field:SerializedName("alt_description")
	val altDescription: String,

	@field:SerializedName("premium")
	val premium: Boolean,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("width")
	val width: Int,

	@field:SerializedName("blur_hash")
	val blurHash: String,

	@field:SerializedName("asset_type")
	val assetType: String,

	@field:SerializedName("links")
	val links: Links,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("promoted_at")
	val promotedAt: Any,

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("slug")
	val slug: String,

	@field:SerializedName("breadcrumbs")
	val breadcrumbs: List<Any>,

	@field:SerializedName("height")
	val height: Int,

	@field:SerializedName("likes")
	val likes: Int
)

data class Ancestry(

	@field:SerializedName("type")
	val type: Type,

	@field:SerializedName("category")
	val category: Category,

	@field:SerializedName("subcategory")
	val subcategory: Subcategory
)

data class AlternativeSlugs(

	@field:SerializedName("de")
	val de: String,

	@field:SerializedName("ko")
	val ko: String,

	@field:SerializedName("pt")
	val pt: String,

	@field:SerializedName("ja")
	val ja: String,

	@field:SerializedName("en")
	val en: String,

	@field:SerializedName("it")
	val it: String,

	@field:SerializedName("fr")
	val fr: String,

	@field:SerializedName("es")
	val es: String
)

data class Subcategory(

	@field:SerializedName("pretty_slug")
	val prettySlug: String,

	@field:SerializedName("slug")
	val slug: String
)

data class Category(

	@field:SerializedName("pretty_slug")
	val prettySlug: String,

	@field:SerializedName("slug")
	val slug: String
)

data class FashionBeauty(

	@field:SerializedName("approved_on")
	val approvedOn: String,

	@field:SerializedName("status")
	val status: String
)

data class ProfileImage(

	@field:SerializedName("small")
	val small: String,

	@field:SerializedName("large")
	val large: String,

	@field:SerializedName("medium")
	val medium: String
)

data class Links(

	@field:SerializedName("followers")
	val followers: String,

	@field:SerializedName("portfolio")
	val portfolio: String,

	@field:SerializedName("following")
	val following: String,

	@field:SerializedName("self")
	val self: String,

	@field:SerializedName("html")
	val html: String,

	@field:SerializedName("photos")
	val photos: String,

	@field:SerializedName("likes")
	val likes: String,

	@field:SerializedName("download")
	val download: String,

	@field:SerializedName("download_location")
	val downloadLocation: String
)

data class BreadcrumbsItem(

	@field:SerializedName("index")
	val index: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("slug")
	val slug: String
)

data class ResultsItemUnsplash(

	@field:SerializedName("topic_submissions")
	val topicSubmissions: TopicSubmissions,

	@field:SerializedName("current_user_collections")
	val currentUserCollections: List<Any>,

	@field:SerializedName("color")
	val color: String,

	@field:SerializedName("sponsorship")
	val sponsorship: Any,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("liked_by_user")
	val likedByUser: Boolean,

	@field:SerializedName("tags")
	val tags: List<TagsItem>,

	@field:SerializedName("urls")
	val urls: Urls,

	@field:SerializedName("alternative_slugs")
	val alternativeSlugs: AlternativeSlugs,

	@field:SerializedName("alt_description")
	val altDescription: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("width")
	val width: Int,

	@field:SerializedName("blur_hash")
	val blurHash: String,

	@field:SerializedName("asset_type")
	val assetType: String,

	@field:SerializedName("links")
	val links: Links,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("promoted_at")
	val promotedAt: String,

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("slug")
	val slug: String,

	@field:SerializedName("breadcrumbs")
	val breadcrumbs: List<Any>,

	@field:SerializedName("height")
	val height: Int,

	@field:SerializedName("likes")
	val likes: Int
)

data class Urls(

	@field:SerializedName("small")
	val small: String,

	@field:SerializedName("small_s3")
	val smallS3: String,

	@field:SerializedName("thumb")
	val thumb: String,

	@field:SerializedName("raw")
	val raw: String,

	@field:SerializedName("regular")
	val regular: String,

	@field:SerializedName("full")
	val full: String
)

data class TopicSubmissions(

	@field:SerializedName("fashion-beauty")
	val fashionBeauty: FashionBeauty
)

data class Social(

	@field:SerializedName("twitter_username")
	val twitterUsername: String,

	@field:SerializedName("paypal_email")
	val paypalEmail: Any,

	@field:SerializedName("instagram_username")
	val instagramUsername: String,

	@field:SerializedName("portfolio_url")
	val portfolioUrl: String
)

data class User(

	@field:SerializedName("total_photos")
	val totalPhotos: Int,

	@field:SerializedName("accepted_tos")
	val acceptedTos: Boolean,

	@field:SerializedName("social")
	val social: Social,

	@field:SerializedName("twitter_username")
	val twitterUsername: String,

	@field:SerializedName("last_name")
	val lastName: Any,

	@field:SerializedName("bio")
	val bio: String,

	@field:SerializedName("total_likes")
	val totalLikes: Int,

	@field:SerializedName("portfolio_url")
	val portfolioUrl: String,

	@field:SerializedName("profile_image")
	val profileImage: ProfileImage,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("total_promoted_illustrations")
	val totalPromotedIllustrations: Int,

	@field:SerializedName("for_hire")
	val forHire: Boolean,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("total_promoted_photos")
	val totalPromotedPhotos: Int,

	@field:SerializedName("location")
	val location: Any,

	@field:SerializedName("links")
	val links: Links,

	@field:SerializedName("total_collections")
	val totalCollections: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("total_illustrations")
	val totalIllustrations: Int,

	@field:SerializedName("first_name")
	val firstName: String,

	@field:SerializedName("instagram_username")
	val instagramUsername: String,

	@field:SerializedName("username")
	val username: String
)

data class TagsItem(

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("source")
	val source: Source
)
