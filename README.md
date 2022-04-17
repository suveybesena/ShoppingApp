# ShoppingApp

Bu readme, developer.android.com'ın App Architecture ile ilgili dokümanından aldığım notların kendi projem ile örneklendirilmiş halidir. 

## <a name="2"></a> App Architecture


  Uygulama mimarisi tasarımı, uygulamalarınızın sağlam, test edilebilir ve sürdürülebilir olmasını sağlamak için önemli bir husustur.
Tipik bir Android uygulamasının birden çok bileşen içerebileceği ve kullanıcıların genellikle kısa bir süre içinde birden çok uygulamayla etkileşime girdiği göz önüne alındığında, uygulamaların farklı türde kullanıcı odaklı iş akışlarına ve görevlere uyum sağlaması gerekir.
Mobil cihazların da kaynakları kısıtlıdır, bu nedenle, işletim sistemi herhangi bir zamanda yenilerine yer açmak için bazı uygulama işlemlerini sonlandırabilir.
Bu koşullar göz önüne alındığında, uygulama bileşenlerininin ayrı ayrı ve arızalı olarak başlatılması mümkündür ve işletim sistemi veya kullanıcı bunları herhangi bir zamanda imha edebilir. Bu olaylar kontrolümüz altında olmadığından, uygulama bileşenlerindeki herhangi bir uygulama verisi veya durumu saklanmamalı veya bellekte tutulmamalıdır ve uygulama bileşenleri birbirine bağlı olmamalıdır.Android uygulamalarının boyutu büyüdükçe, uygulamanın ölçeklenmesini sağlayan, uygulamanın sağlamlığını artıran ve uygulamanın test edilmesini kolaylaştıran bir mimari tanımlamak önemlidir.
Bir diğer önemli ilke, UI’ın veri modelleri ile alınması gerektiğidir.
Veri modelleri, bir uygulamanın verilerini temsil eder. Uygulamanızdaki UI öğelerinden ve diğer bileşenlerden bağımsızdırlar. Bu, kullanıcı arayüzüne ve uygulama bileşeni yaşam döngüsüne bağlı olmadıkları, ancak işletim sistemi uygulamanın sürecini bellekten kaldırmaya karar verdiğinde yine de yok edilecekleri anlamına gelir. Bu sayede;

<ul>
  <li>Android işletim sistemi, kaynakları boşaltmak için uygulamanızı yok ederse, kullanıcılarınız veri kaybetmez.</li>
  <li>Uygulamanız, ağ bağlantısının kesintili olduğu veya kullanılamadığı durumlarda çalışmaya devam eder.</li>
  <li>Uygulama mimarinizi veri modeli sınıflarına dayandırırsanız, uygulamanızı daha test edilebilir ve sağlam hale getirirsiniz.</li>
</ul>


Bir önceki bölümde bahsedilen ortak mimari ilkeler göz önüne alındığında, her uygulamada en az iki katman olmalıdır:
<ul>
  <li>Uygulama verilerini ekranda görüntüleyen UI katmanı.</li>
  <li>Uygulamanızın iş mantığını içeren ve uygulama verilerini ortaya çıkaran veri katmanı.</li>
</ul>

UI katmanının rolü, uygulama verilerini ekranda görüntülemektir. Veriler, kullanıcı etkileşimi (bir düğmeye basmak gibi) veya harici giriş (ağ yanıtı gibi) nedeniyle değiştiğinde, kullanıcı arayüzü değişiklikleri yansıtacak şekilde güncellenmelidir.

UI katmanı iki şeyden oluşur:

<ul>
  <li>Ekrandaki verileri işleyen UI öğeleri.</li>
  <li>Verileri tutan, kullanıcı arayüzüne sunan ve mantığı işleyen state holderlar. (ViewModel sınıfları gibi).</li>
</ul>


## <a name="2"></a> UI 

UI’ın rolü, uygulama verilerini ekranda görüntülemek ve ayrıca kullanıcı etkileşiminin birincil noktası olarak hizmet etmektir. Veriler, kullanıcı etkileşimi (bir düğmeye basmak gibi) veya harici giriş (bir ağ yanıtı gibi) nedeniyle değiştiğinde, kullanıcı arayüzü bu değişiklikleri yansıtacak şekilde güncellenmelidir. UI, veri katmanından alınan ve işlenen mantığın görsel bir temsilidir.
Ancak veri katmanından aldığınız uygulama verileri genellikle görüntülemeniz gereken bilgilerden farklı bir formattadır. Örneğin, UI için verilerin yalnızca bir kısmına ihtiyacınız olabilir veya kullanıcıyla ilgili bilgileri sunmak için iki farklı veri kaynağını birleştirmeniz gerekebilir. Uyguladığınız mantık ne olursa olsun, tam olarak işlemesi için ihtiyaç duyduğu tüm bilgileri UI'ye iletmeniz gerekir. UI katmanı, uygulama veri değişikliklerini UI'nin sunabileceği bir forma dönüştüren ve ardından bunu görüntüleyen ardışık düzendir.

### <a name="3"></a> UI State
UI, kullanıcının gördüğü şeyse, UI state, uygulamanın görmeleri gerektiğini söylediği şeydir. Aynı madalyonun iki yüzü gibi, UI da UI state’in görsel temsilidir. UI state’deki herhangi bir değişiklik, hemen UI'a yansıtılır.
Örnek olarak; Shopping App uygulamasında Lipstick ürünlerini gösterdiğimiz fragment’ın gereksinimlerini karşılamak için, kullanıcı arayüzünü tam olarak oluşturmak için gereken bilgiler, aşağıdaki gibi tanımlanan bir LipstickFeedUiState veri sınıfında kapsüllenebilir:



data class LipstickFeedUiState(

    val error: String? = null,
  
    val isLoading: Boolean = false,
    
    val lipstickItems : List<ProductFeatures>? = null
       
)
 
### <a name="3"></a>Değişmezlik
Yukarıdaki örnekteki UI state tanımı değişmezdir. Bunun en önemli faydası, değişmez nesnelerin, uygulamanın durumu ile ilgili olarak garantiler sağlamasıdır. Bu, UI’ı tek bir role odaklanmak için serbest bırakır: durumu okumak ve kullanıcı arayüzü öğelerini buna göre güncellemek. Sonuç olarak, UI’ın kendisinin verilerin tek kaynağı olmadığı sürece, UI state’I UI’da asla doğrudan değiştirmemelisiniz. Bu ilkeyi ihlal etmek, aynı bilgi parçası için birden fazla doğruluk kaynağına yol açarak veri tutarsızlıklarına sebep olur. 

### <a name="3"></a> UDF
Önceki bölüm, UI state’in, UI'nin oluşturulması için gereken ayrıntıların değişmez bir anlık görüntüsü olduğu anlatıldı. Ancak, uygulamalardaki verilerin dinamik yapısı, durumun zaman içinde değişebileceği anlamına gelir. Bunun nedeni, kullanıcı etkileşimi veya diğer olaylar olabilir.
Bu etkileşimlerin mantığını tanımlamak ve UI state oluşturmak için bir aracıdan yararlanabilir. Bu etkileşimler ve mantıkları kullanıcı arabiriminin kendisinde barındırılabilir, ancak kullanıcı arabirimi adından da anlaşılacağı gibi veri sahibi, üretici, dönüştürücü ve daha fazlası olmaya başladıkça bu durum hantallaşabilir. Ayrıca, test edilebilirliği etkileyebilir, çünkü ortaya çıkan kod, ayırt edilebilir sınırları olmadan sıkı bir şekilde bağlanmış olacaktır. Sonuç olarak, UI state çok basit olmadığı sürece, UI'nin tek sorumluluğu UI state’i tüketmek ve görüntülemek olmalıdır.

Bu bölümde,sorumluluk ayrımını uygulamaya yardımcı olan bir mimari model olan **Tek Yönlü Veri Akışı (UDF)** ele alınmaktadır.

### <a name="3"></a> State holders
  
UI durumunun üretilmesinden sorumlu olan ve o görev için gerekli mantığı içeren sınıflara state holder denir. Bunun için ViewModel önerilir, ancak uygulamanın gereksinimlerine bağlı olarak basit bir sınıf da yeterli olabilir. ViewModel, veri katmanına erişim ile ekran düzeyinde UI state’in yönetimi için önerilen uygulamadır. 
UI ve state üreticisi arasındaki karşılıklı bağımlılığı modellemenin birçok yolu vardır. State’in aşağı aktığı ve event’in yukarı aktığı modele tek yönlü veri akışı (UDF) denir. Bu kalıbın uygulama mimarisi üzerindeki etkileri aşağıdaki gibidir:
  
  <ol>
  <li>ViewModel, UI tarafından tüketilecek durumu tutar ve IU state ile çıkarır. UI state, ViewModel tarafından dönüştürülen uygulama verileridir.</li>
  <li>UI, ViewModel'e kullanıcı olaylarını bildirir.</li>
  <li>ViewModel, kullanıcı eylemlerini yönetir ve durumu günceller.</li>
  <li>Güncellenen durum, kullanıcı arayüzüne geri beslenir.</li>
</ol>

  Ve bu akış bu şekilde devam eder. 
  
UI state’i tanımladıktan ve o state’in üretimini nasıl yöneteceğinizi belirledikten sonraki adım, üretilen durumu UI'a sunmaktır. Durumun üretimini yönetmek için UDF'yi kullandığınız için, üretilen durumu bir akış olarak düşünebilirsiniz; başka bir deyişle, durumun birden çok sürümü zaman içinde üretilecektir. Sonuç olarak, UI state’i LiveData veya StateFlow gibi gözlemlenebilir bir veri tutucuda göstermelisiniz. Bunun nedeni, kullanıcı arayüzünün, verileri doğrudan ViewModel'den manuel olarak çekmek zorunda kalmadan durumda yapılan herhangi bir değişikliğe tepki verebilmesidir. 
Bir UiState akışı oluşturmanın yaygın bir yolu, ViewModel'den değiştirilemez akışın yedeği değiştirilebilir bir akış yazmaktır; örneğin, bir MutableStateFlow<UiState> öğesini bir StateFlow<UiState> olarak kullanıma sunmak.

  
private val _uiState = MutableStateFlow(EyeshadowFeedUiState())
  
var uiState: StateFlow<EyeshadowFeedUiState> = _uiState.asStateFlow()
  

Bir UI durum nesnesi, birbiriyle ilişkili durumları işlemelidir. Bu, daha az tutarsızlığa yol açar ve kodun anlaşılmasını kolaylaştırır. İki farklı akıştaki haber öğelerinin listesini ve yer imlerinin sayısını gösterirseniz, birinin güncellendiği ve diğerinin güncellenmediği bir duruma düşebilirsiniz. Tek bir akış kullandığınızda, her iki öğe de güncel tutulur.
UI state’in UI’da kullanılması
UI’daki UiState nesnelerinin akışını kullanmak için, kullandığınız gözlemlenebilir veri türü için terminal operatörünü kullanırsınız. Örneğin, LiveData için observe( ) yöntemini, Kotlin flows için ise Collect() yöntemini veya varyasyonlarını kullanırsınız.

  
  
lifecycleScope.launch {
  
  
    viewModel.uiState.collect { state ->
  
        state.eyeshadowItems.let { eyeshadowItemsList->
  
            eyeshadowAdapter.differ.submitList(eyeshadowItemsList)
        }
        state.isLoading.let { boolean ->
  
            if (boolean == true){
  
                binding.pbEyeshadow.visibility = View.VISIBLE
  
            }else{
  
                binding.pbEyeshadow.visibility = View.GONE
  
            }
           
        }
  
    }
  
}
  

### <a name="3"></a> UI events
UI event, UI veya ViewModel tarafından UI katmanında işlenmesi gereken eylemlerdir. En yaygın olay türü kullanıcı olaylarıdır. Kullanıcı, uygulamayla etkileşim kurarak (örneğin, ekrana dokunarak veya hareketler oluşturarak) kullanıcı etkinlikleri üretir. Kullanıcı arayüzü daha sonra bu olayları onClick() dinleyicileri gibi callbackler kullanarak tüketir. ViewModel normalde belirli bir kullanıcı olayının iş mantığını işlemekten sorumludur; kullanıcının bazı verileri yenilemek için bir düğmeyi tıklaması gibi. Genellikle, ViewModel bunu, UI’ın çağırabileceği işlevleri açığa çıkararak gerçekleştirir. Kullanıcı olaylarının, örneğin farklı bir ekrana gitme veya bir Snackbar gösterme gibi, kullanıcı arabiriminin doğrudan işleyebileceği UI davranış mantığı da olabilir.
UI, bu olaylar bir kullanıcı arabirimi öğesinin durumunu değiştirmekle ilgiliyse, bunları doğrudan kendi içinde işleyebilir. Olay, ekrandaki verilerin yenilenmesi gibi iş mantığını gerektiriyorsa, ViewModel tarafından işlenmelidir.
Bu şekilde, RecyclerView adapter yalnızca ihtiyaç duyduğu verilerle çalışır: EyeshadowFeedUiState nesnelerinin listesi. Adapter’in ViewModel'in tamamına erişimi yoktur, bu da ViewModel'in sunduğu işlevselliği kötüye kullanma olasılığını azaltır. ViewModel ile yalnızca aktivite sınıfının çalışmasına izin verdiğinizde, sorumlulukları birbirinden ayırırsınız. Bu, görünümler veya RecyclerView Adapter gibi kullanıcı arabirimine özgü nesnelerin doğrudan ViewModel ile etkileşime girmemesini sağlar.

 ## <a name="2"></a>Domain Layer
  

Domain katmanı, UI katmanı ile data katmanı arasında bulunan isteğe bağlı bir katmandır.
Domain katmanı, karmaşık iş mantığını veya birden çok ViewModel tarafından yeniden kullanılan basit iş mantığını kapsüllemekten sorumludur. Bu katman isteğe bağlıdır çünkü tüm uygulamalar bu gereksinimlere sahip olmayacaktır. Yalnızca gerektiğinde, örneğin karmaşıklığı gidermek veya yeniden kullanılabilirliği desteklemek için kullanmalısınız.
Domain Layer;
 
  <ul>
  <li>Kod tekrarını önler./li>
  <li>Domain sınıflarını kullanan sınıflarda okunabilirliği artırır.</li>
  <li>Uygulamanın test edilebilirliğini artırır.</li>
  <li>Seperation of Concerns prensibini uygular. </li>
</ul>

  
Bu sınıfları basit tutmak için, her bir Use Case’in yalnızca tek bir işlevsellik üzerinde sorumluluğu olmalıdır ve bunlar değiştirilebilir veriler içermemelidir. Bunun yerine, kullanıcı arayüzünüzdeki veya veri katmanlarınızdaki değişken verileri işlemeniz gerekir.
Tipik bir uygulama mimarisinde, use case’ler UI katmanındaki ViewModel’lar ile data katmanındaki repositoryler arasında bulunur. Bu, use case sınıflarının genellikle repository sınıflarına bağlı olduğu ve UI katmanıyla, repository’nin yaptığı gibi coroutine’leri (Kotlin için) kullanarak iletişim kurdukları anlamına gelir.
Use case’ler yeniden kullanılabilir mantık içerdiğinden, diğer Use case’ler tarafından da kullanılabilirler. Domain katmanında birden çok Use case’in olması normaldir.
Kotlin'de, operatör değiştiricisiyle invoke() işlevini tanımlayarak Use case sınıfı örneklerini işlev olarak çağrılabilir hale getirebilirsiniz.
  
  
class DeleteProductFromLocalUseCase @Inject constructor(val repository: ShoppingRepository) {
  
    suspend fun invoke(product: ProductFeatures) = flow {
  
        emit(Resource.Loading)
  
        try {
  
            val deleteProduct = repository.deleteProduct(product)
  
            emit(Resource.Success(deleteProduct))
  
        } catch (e: Exception) {
  
            emit(Resource.Error(e.localizedMessage))
  
        }
  
    }
  
}

### <a name="3"></a>Lifecycle
  
Use case’lerin kendi yaşam döngüleri yoktur. Bunun yerine, onları kullanan sınıfın kapsamına girerler. Bu, UI katmanındaki sınıflardan, servislerden veya Application sınıfının kendisinden Use case çağırabileceğiniz anlamına gelir. Use case’ler değiştirilebilir veriler içermemesi gerektiğinden, onu bağımlılık olarak her ilettiğinizde yeni bir Use case sınıfı örneği oluşturmalısınız.

  
  ## <a name="2"></a>Data Layer


UI katmanı, UI ile ilgili durumu ve UI mantığını içerirken, veri katmanı uygulama verilerini business logic’i içerir. Uygulamanıza değer katan business logic’dir; uygulama verilerinin nasıl oluşturulması, saklanması ve değiştirilmesi gerektiğini belirleyen iş kurallarından oluşur.


 Bu endişelerin ayrılması(separation of concerns), veri katmanının birden çok ekranda kullanılmasına, uygulamanın farklı bölümleri arasında bilgi paylaşılmasına ve test için UI dışında iş mantığını yeniden üretmesine olanak tanır.
  
 ### <a name="3"></a>Data Layer mimarisi

Data katmanı, her biri sıfır ila birçok veri kaynağı içerebilen repository’lerden oluşur. Uygulamanızda işlediğiniz her farklı veri türü için bir repository sınıfı oluşturmalısınız. Örneğin, filmlerle ilgili veriler için bir MoviesRepository sınıfı veya ödemelerle ilgili veriler için bir PaymentsRepository sınıfı oluşturabilirsiniz.

Repository  sınıfları aşağıdaki görevlerden sorumludur:
  
  <ul>
  <li>Verileri uygulamanın geri kalanına gösterme.</li>
  <li>Verilerdeki değişiklikleri merkezileştirme.</li>
  <li>Birden çok veri kaynağı arasındaki çakışmaları çözme.</li>
  <li>Uygulamanın geri kalanından veri kaynaklarını soyutlama.</li>
  <li>Business logic içerme..</li>
</ul>
  

Her veri kaynağı sınıfı, dosya, ağ kaynağı veya yerel veritabanı olabilen yalnızca tek bir veri kaynağıyla çalışma sorumluluğuna sahip olmalıdır. Veri kaynağı sınıfları, veri işlemleri için uygulama ile sistem arasındaki köprüdür.
Hiyerarşideki diğer katmanlar, veri kaynaklarına asla doğrudan erişmemelidir; veri katmanına giriş noktaları her zaman repository sınıflarıdır. State holder sınıfları veya use case sınıfları  hiçbir zaman doğrudan bağımlılık olarak bir veri kaynağına sahip olmamalıdır. Respository sınıflarını giriş noktaları olarak kullanmak, mimarinin farklı katmanlarının bağımsız olarak ölçeklenmesini sağlar.
Dependeny Injeciton pratikleri ile , repository veri kaynaklarını constructor’unda bağımlılık olarak alır:
  
  
class ShoppingRepository @Inject constructor(
  
    private val remoteDataSource: ProductAPI,
  
    private val localDataSource: ProductDAO
  
) {


Veri katmanındaki sınıflar genellikle CRUD çağrıları gerçekleştirme veya zaman içindeki veri değişikliklerinden haberdar olma işlevlerini sunar. Veri katmanı, bu durumların her biri için aşağıdakileri göstermelidir:
  
  <ul>
  <li>Tek seferlik işlemler için: Kotlin’de suspend function ile kullanılırlar.</li>
  <li>Second item</li>
  <li>Zaman içindeki veri değişikliklerinden haberdar olmak için: Yine kotlinde, flow ile kullanılırlar. </li>
  <li>Fourth item</li>
</ul>

### <a name="3"></a>Çok seviyeli repositoryler

Daha karmaşık iş gereksinimleri içeren bazı durumlarda, bir repository’nin diğer repository’ye bağlı olması gerekebilir. Bunun nedeni, ilgili verilerin birden çok veri kaynağından bir toplama olması veya sorumluluğun başka bir repository sınıfında kapsüllenmesi gerekmesi olabilir.

### <a name="3"></a>Threading
  
Data source ve repositorylerden yapılan arama güvenli olmalıdır; main thread’den  arama yapmak güvenli olmalıdır. Kotlin kullanıcıları için coroutine’ler önerilen seçenektir.
### <a name="3"></a>Lifecycle

Bir sınıf bellek içi veriler (örneğin bir önbellek) içeriyorsa, o sınıfın aynı instance’ı belirli bir süre için yeniden kullanmak isteyebilirsiniz. Sınıfın sorumluluğu tüm uygulama için çok önemliyse, o sınıfın bir örneğini Application sınıfına dahil edebilirsiniz. Bu, örneğin uygulamanın yaşam döngüsünü takip etmesini sağlar. Alternatif olarak, aynı örneği yalnızca uygulamanızdaki belirli bir akışta (örneğin, kayıt veya oturum açma akışı) yeniden kullanmanız gerekiyorsa, örneği o akışın yaşam döngüsünün sahibi olan sınıfla kapsamanız gerekir.
Her örneğin yaşam döngüsü, uygulamanızda bağımlılıkların nasıl sağlanacağına karar vermede kritik bir faktördür. Bağımlılıkların yönetildiği ve bağımlılık kapsayıcılarına göre kapsamlandırılabileceği en iyi bağımlılık enjeksiyon uygulamalarını izlemeniz önerilir. Bunun için Hilt önerilir. 

  ## <a name="2"></a>Model

Veri katmanından göstermek istediğiniz veri modelleri, farklı veri kaynaklarından aldığınız bilgilerin bir alt kümesi olabilir. İdeal olarak, farklı veri kaynakları (hem ağ hem de yerel) yalnızca uygulamanızın ihtiyaç duyduğu bilgileri döndürmelidir; ama bu çoğu zaman böyle değildir.
Best practice model sınıflarını ayırmak ve depolarınızın yalnızca hiyerarşinin diğer katmanlarının gerektirdiği verileri göstermesini sağlamaktır.  

  
data class MakeupItemResponseItem(
  
    @SerializedName("id")
  
    val id: Int?,
  
    @SerializedName("api_featured_image")
  
    val apiFeaturedİmage: String?,
  
    @SerializedName("brand")
  
    val brand: String?,
  
    @SerializedName("category")
  
    val category: String?,
  
    @SerializedName("created_at")
  
    val createdAt: String?,
  
    @SerializedName("description")
  
    val description: String?,
  
    @SerializedName("image_link")
  
    val imageLink: String?,
  
    @SerializedName("name")
  
    val name: String?,
  
    @SerializedName("price")
  
    val price: String?,
  
    @SerializedName("product_api_url")
  
    val productApiUrl: String?,
  
    @SerializedName("product_link")
  
    val productLink: String?,
  
    @SerializedName("product_type")
  
    val productType: String?,
  
    @SerializedName("rating")
  
    val rating: Double?,
  
    @SerializedName("updated_at")
  
    val updatedAt: String?,
  
    @SerializedName("website_link")
  
    val websiteLink: String?
  
)

fun MakeupItemResponseItem.toProductFeatures() = ProductFeatures(
 
    productId = id,
  
    productName = brand,
  
    productLink = productLink,
  
    productImage = imageLink,
  
    productCategory = category,
  
    productPrice = price
  
)

 
Model sınıflarını ayırmak aşağıdaki şekillerde faydalıdır:
  
  <ul>
  <li>Verileri yalnızca ihtiyaç duyulana indirgeyerek uygulama belleğinden tasarruf sağlar.</li>
  <li>Second item</li>
  <li>Harici veri türlerini uygulamanız tarafından kullanılan veri türlerine uyarlar; örneğin, uygulamanız tarihleri temsil etmek için farklı bir veri türü kullanabilir.</li>
</ul>

  
  <h3>App Images</h3>
<img src="https://user-images.githubusercontent.com/85364012/163718921-c97b85ae-6d70-4e22-a87b-5c76b9980312.jpeg"width=30% height=30%>
<img src="https://user-images.githubusercontent.com/85364012/163718923-bbd91739-3a27-424f-8824-996c94bd7e31.jpeg"width=30% height=30%>
<img src="https://user-images.githubusercontent.com/85364012/163718924-3a47e1f9-41b8-4c75-bbc2-165c7dbd78ae.jpeg"width=30% height=30%>
  
  
  
  
  

