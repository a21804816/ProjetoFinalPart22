import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinalpart1.NavigationManager
import com.example.projetofinalpart1.data.ConnectivityUtil
import com.example.projetofinalpart1.data.FilmeRoom
import com.example.projetofinalpart1.data.FilmsDatabase
import com.example.projetofinalpart1.databinding.FilmeItemBinding
import com.example.projetofinalpart1.model.Filme
import com.example.projetofinalpart1.model.repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class FilmeAdapter(
    private val onClick: (String) -> Unit,
    private var items: List<Filme> = listOf()

) : RecyclerView.Adapter<FilmeAdapter.FilmeViewHolder>() {

    private lateinit var objetoFilme: FilmeRoom

    class FilmeViewHolder(val binding: FilmeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val movieImage: ImageView = binding.filmeFotografiaImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        return FilmeViewHolder(
            FilmeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {

        /*objetoFilme = FilmeRoom(FilmsDatabase.getInstance(holder.itemView.context).filmDao())
        var listaFilmes = mutableListOf<String>()
        CoroutineScope(Dispatchers.IO).launch {
            val filmesCinemasList = objetoFilme.getFilmesCinemasList()

            var (listaFilmes, listaCinemas) = filmesCinemasList.unzip()


            var filme1 = objetoFilme.getFilmByTitle(listaFilmes[0])
            println(filme1!!.imdbID)
            var cinema1 = objetoFilme.verificarCinemaExiste(listaCinemas[0],holder.itemView.context)
            println(cinema1)

            if (items[position].title == listaFilmes[position]){
                objetoFilme.getFilmByTitle(items[position].title)
            }
        }*/

        val orientation = holder.itemView.context.resources.configuration.orientation
        holder.itemView.setOnClickListener { onClick(items[position].imdbID) }
        holder.binding.nomeFilmeEditText.text = items[position].title
        holder.binding.cinemaEditText.text = items[position].userCinema
        holder.binding.avaliacaoValor.text = items[position].userRating
        holder.binding.dataEditText.text = items[position].userDate
        holder.binding.observacoesEditText.text = items[position].userObservations
        val url = items[position].poster
        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = getBitmapFromURL(holder.movieImage.context, url)
            if (bitmap != null) {
                holder.binding.filmeFotografiaImageView.setImageBitmap(bitmap)
            }
        }
        holder.binding.cinemaEditText.visibility =
            if (orientation == Configuration.ORIENTATION_PORTRAIT) View.GONE else View.VISIBLE
        holder.binding.observacoesEditText.visibility =
            if (orientation == Configuration.ORIENTATION_PORTRAIT) View.GONE else View.VISIBLE

        holder.binding.avaliacaoValor.visibility = View.VISIBLE
    }

    override fun getItemCount(): Int = items.size

    fun setData(newItems: List<Filme>) {
        items = newItems
        notifyDataSetChanged()
    }

    private suspend fun getBitmapFromURL(context: Context, src: String): Bitmap? {
        val fileName = getFileNameFromURL(src)
        val cacheDir = context.cacheDir
        val file = File(cacheDir, fileName)

        return if (file.exists()) {
            decodeBitmapFromFile(file)
        } else {
            if (ConnectivityUtil.isOnline(context)) {
                withContext(Dispatchers.IO) {
                    try {
                        val url = URL(src)
                        val connection = url.openConnection() as HttpURLConnection
                        connection.doInput = true
                        connection.connect()
                        val inputStream = connection.inputStream
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        saveBitmapToCache(context, bitmap, fileName)
                        bitmap
                    } catch (e: IOException) {
                        e.printStackTrace()
                        null
                    }
                }
            } else {
                null
            }
        }
    }

    private fun saveBitmapToCache(context: Context, bitmap: Bitmap, fileName: String) {
        val cacheDir = context.cacheDir
        val file = File(cacheDir, fileName)

        try {
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun decodeBitmapFromFile(file: File): Bitmap? {
        return try {
            val inputStream = FileInputStream(file)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun getFileNameFromURL(url: String): String {
        return url.substringAfterLast("/")
    }
}

