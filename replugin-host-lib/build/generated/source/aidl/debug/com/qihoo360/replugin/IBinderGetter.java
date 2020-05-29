/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/clark/StudioProjects/Learn_Replugin/replugin-host-lib/src/main/aidl/com/qihoo360/replugin/IBinderGetter.aidl
 */
package com.qihoo360.replugin;
/**
 * Binder的获取器，可用于延迟加载IBinder的情况。
 * <p>
 * 目前用于：
 * <p>
 * * RePlugin.registerGlobalBinderDelayed
 *
 * @author RePlugin Team
 */
public interface IBinderGetter extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.qihoo360.replugin.IBinderGetter
{
private static final java.lang.String DESCRIPTOR = "com.qihoo360.replugin.IBinderGetter";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.qihoo360.replugin.IBinderGetter interface,
 * generating a proxy if needed.
 */
public static com.qihoo360.replugin.IBinderGetter asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.qihoo360.replugin.IBinderGetter))) {
return ((com.qihoo360.replugin.IBinderGetter)iin);
}
return new com.qihoo360.replugin.IBinderGetter.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
java.lang.String descriptor = DESCRIPTOR;
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(descriptor);
return true;
}
case TRANSACTION_get:
{
data.enforceInterface(descriptor);
android.os.IBinder _result = this.get();
reply.writeNoException();
reply.writeStrongBinder(_result);
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements com.qihoo360.replugin.IBinderGetter
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * 获取IBinder对象
     */
@Override public android.os.IBinder get() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.IBinder _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_get, _data, _reply, 0);
_reply.readException();
_result = _reply.readStrongBinder();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_get = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
     * 获取IBinder对象
     */
public android.os.IBinder get() throws android.os.RemoteException;
}
