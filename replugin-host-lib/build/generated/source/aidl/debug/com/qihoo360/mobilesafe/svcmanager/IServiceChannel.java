/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/clark/StudioProjects/Learn_Replugin/replugin-host-lib/src/main/aidl/com/qihoo360/mobilesafe/svcmanager/IServiceChannel.aidl
 */
package com.qihoo360.mobilesafe.svcmanager;
public interface IServiceChannel extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.qihoo360.mobilesafe.svcmanager.IServiceChannel
{
private static final java.lang.String DESCRIPTOR = "com.qihoo360.mobilesafe.svcmanager.IServiceChannel";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.qihoo360.mobilesafe.svcmanager.IServiceChannel interface,
 * generating a proxy if needed.
 */
public static com.qihoo360.mobilesafe.svcmanager.IServiceChannel asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.qihoo360.mobilesafe.svcmanager.IServiceChannel))) {
return ((com.qihoo360.mobilesafe.svcmanager.IServiceChannel)iin);
}
return new com.qihoo360.mobilesafe.svcmanager.IServiceChannel.Stub.Proxy(obj);
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
case TRANSACTION_getService:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
android.os.IBinder _result = this.getService(_arg0);
reply.writeNoException();
reply.writeStrongBinder(_result);
return true;
}
case TRANSACTION_addService:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
android.os.IBinder _arg1;
_arg1 = data.readStrongBinder();
this.addService(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_addServiceDelayed:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
com.qihoo360.replugin.IBinderGetter _arg1;
_arg1 = com.qihoo360.replugin.IBinderGetter.Stub.asInterface(data.readStrongBinder());
this.addServiceDelayed(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_removeService:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
this.removeService(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getPluginService:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
android.os.IBinder _arg2;
_arg2 = data.readStrongBinder();
android.os.IBinder _result = this.getPluginService(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeStrongBinder(_result);
return true;
}
case TRANSACTION_onPluginServiceRefReleased:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.onPluginServiceRefReleased(_arg0, _arg1);
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements com.qihoo360.mobilesafe.svcmanager.IServiceChannel
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
@Override public android.os.IBinder getService(java.lang.String serviceName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.IBinder _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(serviceName);
mRemote.transact(Stub.TRANSACTION_getService, _data, _reply, 0);
_reply.readException();
_result = _reply.readStrongBinder();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void addService(java.lang.String serviceName, android.os.IBinder service) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(serviceName);
_data.writeStrongBinder(service);
mRemote.transact(Stub.TRANSACTION_addService, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addServiceDelayed(java.lang.String serviceName, com.qihoo360.replugin.IBinderGetter getter) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(serviceName);
_data.writeStrongBinder((((getter!=null))?(getter.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_addServiceDelayed, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void removeService(java.lang.String serviceName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(serviceName);
mRemote.transact(Stub.TRANSACTION_removeService, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public android.os.IBinder getPluginService(java.lang.String pluginName, java.lang.String serviceName, android.os.IBinder deathMonitor) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.IBinder _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(pluginName);
_data.writeString(serviceName);
_data.writeStrongBinder(deathMonitor);
mRemote.transact(Stub.TRANSACTION_getPluginService, _data, _reply, 0);
_reply.readException();
_result = _reply.readStrongBinder();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void onPluginServiceRefReleased(java.lang.String pluginName, java.lang.String serviceName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(pluginName);
_data.writeString(serviceName);
mRemote.transact(Stub.TRANSACTION_onPluginServiceRefReleased, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_addService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_addServiceDelayed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_removeService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getPluginService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_onPluginServiceRefReleased = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
}
public android.os.IBinder getService(java.lang.String serviceName) throws android.os.RemoteException;
public void addService(java.lang.String serviceName, android.os.IBinder service) throws android.os.RemoteException;
public void addServiceDelayed(java.lang.String serviceName, com.qihoo360.replugin.IBinderGetter getter) throws android.os.RemoteException;
public void removeService(java.lang.String serviceName) throws android.os.RemoteException;
public android.os.IBinder getPluginService(java.lang.String pluginName, java.lang.String serviceName, android.os.IBinder deathMonitor) throws android.os.RemoteException;
public void onPluginServiceRefReleased(java.lang.String pluginName, java.lang.String serviceName) throws android.os.RemoteException;
}
