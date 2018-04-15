package br.com.projetofinal.iceapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import br.com.projetofinal.iceapp.autentication.Credenciais;
import br.com.projetofinal.iceapp.model.Pedido;
import br.com.projetofinal.iceapp.model.Produto;
import br.com.projetofinal.iceapp.model.Usuario;
import retrofit.Call;

import retrofit.GsonConverterFactory;

import retrofit.Retrofit;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;


/**
 * Created by Marcelo on 18/09/2016.
 */
public class ApiWeb {

    //SEMPRE ALTERAR NO CAMINHO O IP, COLOCAR O DA MÁQUINA AONDE ESTÁ O SERVIDOR WEB
    public static final String BASE_URL = "http://192.168.0.17:8080/";

    public static Rotas rotasApi;

    public static Rotas getRotas() {
        if (rotasApi == null) {


//            JsonSerializer<Date> ser = new JsonSerializer<Date>() {
//                @Override
//                public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext
//                        context) {
//                    return src == null ? null : new JsonPrimitive(src.getTime());
//                }
//            };
//
//            JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
//                @Override
//                public Date deserialize(JsonElement json, Type typeOfT,
//                                        JsonDeserializationContext context) throws JsonParseException {
//                    return json == null ? null : new Date(json.getAsLong());
//                }
//            };

            //Serializador Client  Json
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateDeserializer())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            //Inicializa o Rest Client
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            //Objeto rest
            rotasApi = retrofit.create(Rotas.class);
        }

        return rotasApi;
    }

    public interface Rotas{
        @POST("/usuario/salvar")
        Call<Usuario> cadastrarUsuario(@Body Usuario usuario);
        @POST("/produto/salvar")
        Call<Produto> cadastrarProduto(@Body Produto produto);
        @GET("/produto/listar")
        Call<List<Produto>> listarProduto();
        @POST("/usuario/autenticar")
        Call<Usuario> autenticarUsuario(@Body Credenciais credencial);
        @POST("/pedido/adicionar")
        Call<Pedido> adicionarPedido(@Body Pedido pedido);
        @GET("/pedido/listar")
        Call<List<Pedido>> listarPedidoPorUser(@Header("id") Integer id);
        @GET("/pedido/listarContasPagar")
        Call<List<Pedido>> listarPedidoContasPagar(@Header("id") Integer id);
        @POST("/pedido/editarStatus")
        Call<Pedido> editarStatus(@Body Pedido pedido);
        @GET("/pedido/listarTodos")
        Call<List<Pedido>> listarTodosPedido();
        @GET("/usuario/listar")
        Call<List<Usuario>> buscarTodos();
        @POST("/usuario/enviarCobranca")
        Call<Usuario> enviarCobranca(@Body Pedido pedido);
    }

}
 class DateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        String date = element.getAsString();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            return format.parse(date);
        } catch (ParseException exp) {
            System.err.println("Failed to parse Date:");
            return null;
        }
    }
}
