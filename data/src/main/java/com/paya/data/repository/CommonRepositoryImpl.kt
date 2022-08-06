package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.network.remote_api.CommonService
import com.paya.data.sharedpreferences.PreferenceHelper
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.remote.*
import com.paya.domain.models.repo.*
import com.paya.domain.repository.CommonRepository
import com.paya.domain.tools.Resource
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(
    private val commonService: CommonService,
    private val cityRemoteRepoMapper: Mapper<List<ProvinceRemoteModel>, List<ProvinceRepoModel>>,
    private val checkVersionRemoteRepoMapper: Mapper<CheckVersionRemoteModel, CheckVersionRepoModel>,
    private val configRemoteRepoMapper: Mapper<ConfigRemoteModel, ConfigRepoModel>,
    private val preferenceHelper: PreferenceHelper,
) : CommonRepository {

    override suspend fun getCity(): Resource<List<ProvinceRepoModel>> {
        val response = commonService.getCity(preferenceHelper.getAccessToken())
        return getResourceFromApiResponse(response) {
            cityRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun checkVersion(version: String): Resource<CheckVersionRepoModel> {
        val response = commonService.checkVersion(version)
        return getResourceFromApiResponse(response) {
            checkVersionRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun getConfig(): Resource<ConfigRepoModel> {
        val response = commonService.getConfig(preferenceHelper.getAccessToken())
        return getResourceFromApiResponse(response) {
            configRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun saveAppLink(appLink: String): Resource<Unit> {
        preferenceHelper.setAppLink(appLink)
        return Resource.success(Unit, 200)
    }

    override suspend fun getAppLink(): Resource<GetAppLinkRepoModel> {
        return preferenceHelper.getAppLink()
    }

    override suspend fun getWhySoodinowList(): Resource<List<WhySoodinowModel>> {
        val firstWhySoodinowModel = WhySoodinowModel(
            title = "مدیر ثروت چیست ؟",
            description = "مدیریت ثروت یکی از پیشرفته ترین انواع  سرویس های مشاوره و مدیریت امور مربوط به دارایی افراد است. این سرویس برای مشتریان خود با تدوین برنامه و استراتژی های دقیق و مشخص این خدمات را ارائه می دهد هدف مدیریت ثروت کمک به فرد  برای حفظ دارایی، کاهش ریسک های مالی و افزایش سرمایه و پس انداز در زمان مشخص است. \n" +
                    "این نوع مشاوره مالی فراتر از انتخاب سرمایه گذاری است. این یک سرویس برتر است که برای پاسخگویی به نیازهای منحصر به فرد افراد طراحی شده است.در مدل های جدید مدیریت ثروت در سطح دنیا ،از تکنولوژی های هوشمند زیادی همچون هوش مصنوعی ، علم داده ، الگوریتم ها و برای ارائه خدمات به مشتریان استفاده می شود که این امر سبب آن شده تا هزینه خدمات مدیریت ثروت که پیش از این بسیار بالا بوده ، کاهش یافته و امکان استفاده از این خدمات برای همگان بوجود آید "
        )

		val secondWhySoodinowModel = WhySoodinowModel(
			title = "سودینو چیست ؟",
			description = "شرکت مدیریت ثروت پایا آمیخته ای از دانش مالی ،تکنولوژِی و نوآوری و متشکل از بهترین مختصصین این حوزه است و  به دنبال باور خود به انجام مدیریت ثروت در بازارهای مختلف مالی با ریسک های گوناگون از طریق به کارگیری سیستم های هوشمند و نوآور برای حفظ دارایی سرمایه گذاران ، کسب بهترین بازدهی و کمترین ضرر به تناسب هر سرمایه گذار با کمترین هزینه  می باشد.\n" +
					"ما در این راه علاوه بر مدیریت ثروت به توسعه خدماتی که منجر به افزایش و تقویت فرهنگ سرمایه گذاری غیر مستقیم و رفاه سرمایه گذاران در بستری امن می شود می پردازیم و با همکاری، تخصص و رویکردهای نوآورانه  به تلاش در جهت دستیابی به یکی از ارزش های اصلی سودینو، توسعه عدالت اجتماعی در سرمایه گذاری می پردازیم\n"
		)

		val thirdWhySoodinowModel = WhySoodinowModel(
			title = "سودینو چگونه کار می کند ؟",
			description = "سودینو یک مدیر ثروت است که با استفاده از تکنولوژی های بروزی این خدمت را به شکلی هوشمند انجام می دهد و با بهره مندی از تیمی متخصص و باتجربه ابزارهای هوشمند   برنامه ریزی و بهینه سازی را با منابع حرفه ای ترکیب می کنند تا به حفظ و افزایش دارایی ها متناسب با شرایط مختلف به شما کمک کنند\n"
		)

		var list: List<WhySoodinowModel> = listOf(firstWhySoodinowModel, secondWhySoodinowModel, thirdWhySoodinowModel)
		return Resource.success(list,200)
    }

    override suspend fun getAboutUsContent(): Resource<List<AboutUsModel>> {

        val firstAboutUsModel = AboutUsModel(
            title = "مدیر ثروت چیست ؟",
            description = "سودینو متشکل از چندین خدمت گوناگون متناسب با نیاز های مختلف سرمایه گذاران می باشد که تمام تمرکز سودینو انجام این خدمات به بهترین شکل می باشد تا منافع ناشی از آن سبب بهبود زندگی افراد جامعه باشد"
        )

        val secondAboutUsModel = AboutUsModel(
            title = "سرمایه گذاری هوشمند ",
            description = "اگر زمان کافی یا دانش کافی سرمایه گذاری ندارید و یا از هزینه های بالا رویکرد های سنتی مدیریت سرمایه گذاری ناراضی هستید و سرمایه گذاری را برای رسیدن به اهداف خودتون و خانوادتون امری ضروری می دونید \n" +
                    "می تونید از سرویس پیشرفته سرمایه گذاری هوشمند سودینو استفاده کنید که با اتصال الگوریتم های هوشمندش به حساب کارگزاری تون امکان سرمایه گذاری و مدیریت سرمایه  به شکل های  مختلف و هوشمند را براتون فراهم میاره \n"
        )

        val thirdAboutUsModel = AboutUsModel(
            title = "امنیت و سهولت در سرمایه گذاری",
            description = "واریز و برداشت سرمایه در سریع ترین زمان ممکن و انجام سرمایه گذاری ها در بستر امن حساب کارگزاری فارابی",
            hasBox = false
        )

        val fourthAboutUsModel = AboutUsModel(
            title = "سرمایه گذاری خودکار ",
            description = "انجام خودکار تمامی فرایند های انجام سرمایه گذاری به شکلی هوشمند و سریع ",
            hasBox = false
        )

        val fifthAboutUsModel = AboutUsModel(
            title = "مدیریت سرمایه گذاری مداوم",
            description = "حفاظت مداوم از سرمایه گذاری با بهینه سازی و تغییر،  ترکیب یا دارایی های سبد سرمایه گذاری متناسب با تغییرات و شرایط بازار های مالی",
            hasBox = false
        )

        val sixthAboutUsModel = AboutUsModel(
            title = "سرمایه گذاری هوشمند",
            description = " پایش و تحلیل مدوام شرایط بازار های مالی و تشکیل هوشمند  بهینه ترین سبد های سرمایه گذاری با ریسک های مختلف",
            hasBox = false
        )

        val seventhAboutUsModel = AboutUsModel(
            title = "سرمایه گذاری هوشمند ",
            description = "سودینو معتقد است تعهد در کار حرف اول را می زند از این رو متعهد به حفظ منافع شما می باشد و درست ترین و بهترین انتخاب را برای شما به ارمغان می آورد"
        )

        val eighthAboutUsModel = AboutUsModel(
            title = "امنیت سودینو",
            description = "ایجاد بستری هوشمند، امن و قابل اعتماد برای همه افراد جامعه با هرسطح از سرمایه مهمترین هدف سودینو است، تا سرمایه گذار بتواند از فرصت های مدیریت ثروت با اطمینان خاطر استفاده کند و آینده مالی بهتری را برای خود رقم بزند"
        )

        var list: List<AboutUsModel> = listOf(
            firstAboutUsModel,
            secondAboutUsModel,
            thirdAboutUsModel,
            fourthAboutUsModel,
            fifthAboutUsModel,
            sixthAboutUsModel,
            seventhAboutUsModel,
            eighthAboutUsModel
        )
        return Resource.success(list,200)
    }


}