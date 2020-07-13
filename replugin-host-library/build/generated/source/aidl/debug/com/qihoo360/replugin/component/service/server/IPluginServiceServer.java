/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/clark/StudioProjects/Learn_Replugin/replugin-host-library/src/main/aidl/com/qihoo360/replugin/component/service/server/IPluginServiceServer.aidl
 */
package com.qihoo360.replugin.component.service.server;
/**
 * 负责Server端的服务调度、提供等工作，是服务的提供方，核心类之一
 *
 * @hide 框架内部使用
 * @author RePlugin Team
 */
public interface IPluginServiceServer extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.qihoo360.replugin.component.service.server.IPluginServiceServer
{
private static final java.lang.String DESCRIPTOR = "com.qihoo360.replugin.component.service.server.IPluginServiceServer";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.qihoo360.replugin.component.service.server.IPluginServiceServer interface,
 * generating a proxy if needed.
 */
public static com.qihoo360.replugin.component.service.server.IPluginServiceServer asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.qihoo360.replugin.component.service.server.IPluginServiceServer))) {
return ((com.qihoo360.replugin.component.service.server.IPluginServiceServer)iin);
}
return new com.qihoo360.replugin.component.service.server.IPluginServiceServer.Stub.Proxy(obj);
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
case TRANSACTION_startService:
{
data.enforceInterface(descriptor);
android.content.Intent _arg0;
if ((0!=data.readInt())) {
_arg0 = android.content.Intent.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
android.os.Messenger _arg1;
if ((0!=data.readInt())) {
_arg1 = android.os.Messenger.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
android.content.ComponentName _result = this.startService(_arg0, _arg1);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_stopService:
{
data.enforceInterface(descriptor);
android.content.Intent _arg0;
if ((0!=data.readInt())) {
_arg0 = android.content.Intent.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
android.os.Messenger _arg1;
if ((0!=data.readInt())) {
_arg1 = android.os.Messenger.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
int _result = this.stopService(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_bindService:
{
data.enforceInterface(descriptor);
android.content.Intent _arg0;
if ((0!=data.readInt())) {
_arg0 = android.content.Intent.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.qihoo360.loader2.mgr.IServiceConnection _arg1;
_arg1 = com.qihoo360.loader2.mgr.IServiceConnection.Stub.asInterface(data.readStrongBinder());
int _arg2;
_arg2 = data.readInt();
android.os.Messenger _arg3;
if ((0!=data.readInt())) {
_arg3 = android.os.Messenger.CREATOR.createFromParcel(data);
}
else {
_arg3 = null;
}
int _result = this.bindService(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_unbindService:
{
data.enforceInterface(descriptor);
com.qihoo360.loader2.mgr.IServiceConnection _arg0;
_arg0 = com.qihoo360.loader2.mgr.IServiceConnection.Stub.asInterface(data.readStrongBinder());
boolean _result = this.unbindService(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_dump:
{
data.enforceInterface(descriptor);
java.lang.String _result = this.dump();
reply.writeNoException();
reply.writeString(_result);
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements com.qihoo360.replugin.component.service.server.IPluginServiceServer
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
@Override public android.content.ComponentName startService(android.content.Intent intent, android.os.Messenger client) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.content.ComponentName _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((intent!=null)) {
_data.writeInt(1);
intent.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((client!=null)) {
_data.writeInt(1);
client.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_startService, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.content.ComponentName.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int stopService(android.content.Intent intent, android.os.Messenger client) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((intent!=null)) {
_data.writeInt(1);
intent.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((client!=null)) {
_data.writeInt(1);
client.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_stopService, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int bindService(android.content.Intent intent, com.qihoo360.loader2.mgr.IServiceConnection conn, int flags, android.os.Messenger client) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((intent!=null)) {
_data.writeInt(1);
intent.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((conn!=null))?(conn.asBinder()):(null)));
_data.writeInt(flags);
if ((client!=null)) {
_data.writeInt(1);
client.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_bindService, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean unbindService(com.qihoo360.loader2.mgr.IServiceConnection conn) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((conn!=null))?(conn.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unbindService, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String dump() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dump, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_startService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_stopService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_bindService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_unbindService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_dump = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public android.content.ComponentName startService(android.content.Intent intent, android.os.Messenger client) throws android.os.RemoteException;
public int stopService(android.content.Intent intent, android.os.Messenger client) throws android.os.RemoteException;
public int bindService(android.content.Intent intent, com.qihoo360.loader2.mgr.IServiceConnection conn, int flags, android.os.Messenger client) throws android.os.RemoteException;
public boolean unbindService(com.qihoo360.loader2.mgr.IServiceConnection conn) throws android.os.RemoteException;
public java.lang.String dump() throws android.os.RemoteException;
}
