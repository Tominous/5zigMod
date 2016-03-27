package eu.the5zig.util.io.http;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class HttpClient
{
  public static final int TIMEOUT = 5000;
  private static final Cache<String, InetAddress> addressCache = CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.MINUTES).build();
  
  public static void get(String url, EventLoopGroup eventLoop, final HttpResponseCallback callback)
  {
    Preconditions.checkNotNull(url, "url");
    Preconditions.checkNotNull(eventLoop, "eventLoop");
    Preconditions.checkNotNull(callback, "callBack");
    
    URI uri = URI.create(url);
    
    Preconditions.checkNotNull(uri.getScheme(), "scheme");
    Preconditions.checkNotNull(uri.getHost(), "host");
    boolean ssl = uri.getScheme().equals("https");
    int port = uri.getPort();
    if (port == -1) {
      if (uri.getScheme().equals("http")) {
        port = 80;
      } else if (uri.getScheme().equals("https")) {
        port = 443;
      } else {
        throw new IllegalArgumentException("Unknown scheme " + uri.getScheme());
      }
    }
    InetAddress inetHost = (InetAddress)addressCache.getIfPresent(uri.getHost());
    if (inetHost == null)
    {
      try
      {
        inetHost = InetAddress.getByName(uri.getHost());
      }
      catch (UnknownHostException ex)
      {
        callback.call(null, -1, ex);
        return;
      }
      addressCache.put(uri.getHost(), inetHost);
    }
    ChannelFutureListener future = new ChannelFutureListener()
    {
      public void operationComplete(ChannelFuture future)
        throws Exception
      {
        if (future.isSuccess())
        {
          String path = this.val$uri.getRawPath() + (this.val$uri.getRawQuery() == null ? "" : new StringBuilder().append("?").append(this.val$uri.getRawQuery()).toString());
          
          HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, path);
          request.headers().set("Host", this.val$uri.getHost());
          
          future.channel().writeAndFlush(request);
        }
        else
        {
          callback.call(null, -1, future.cause());
        }
      }
    };
    ((Bootstrap)((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().channel(NioSocketChannel.class)).group(eventLoop)).handler(new HttpInitializer(callback, ssl, uri.getHost(), port))).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Integer.valueOf(5000))).remoteAddress(inetHost, port).connect().addListener(future);
  }
}
