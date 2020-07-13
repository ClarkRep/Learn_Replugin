/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/clark/StudioProjects/Learn_Replugin/replugin-host-library/src/main/aidl/com/qihoo360/replugin/packages/IPluginManagerServer.aidl
 */
package com.qihoo360.replugin.packages;
/**
 * 插件管理器。用来控制插件的安装、卸载、获取等。运行在常驻进程中
 * <p>
 * 补充：涉及到插件交互、运行机制有关的管理器，在IPluginHost中
 *
 * @author RePlugin Team
 */
public interface IPluginManagerServer extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.qihoo360.replugin.packages.IPluginManagerServer
{
private static final java.lang.String DESCRIPTOR = "com.qihoo360.replugin.packages.IPluginManagerServer";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.qihoo360.replugin.packages.IPluginManagerServer interface,
 * generating a proxy if needed.
 */
public static com.qihoo360.replugin.packages.IPluginManagerServer asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.qihoo360.replugin.packages.IPluginManagerServer))) {
return ((com.qihoo360.replugin.packages.IPluginManagerServer)iin);
}
return new com.qihoo360.replugin.packages.IPluginManagerServer.Stub.Proxy(obj);
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
case TRANSACTION_install:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
com.qihoo360.replugin.model.PluginInfo _result = this.install(_arg0);
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
case TRANSACTION_uninstall:
{
data.enforceInterface(descriptor);
com.qihoo360.replugin.model.PluginInfo _arg0;
if ((0!=data.readInt())) {
_arg0 = com.qihoo360.replugin.model.PluginInfo.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.uninstall(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_load:
{
data.enforceInterface(descriptor);
java.util.List<com.qihoo360.replugin.model.PluginInfo> _result = this.load();
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_updateAll:
{
data.enforceInterface(descriptor);
java.util.List<com.qihoo360.replugin.model.PluginInfo> _result = this.updateAll();
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_updateUsed:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.updateUsed(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_getRunningPlugins:
{
data.enforceInterface(descriptor);
com.qihoo360.replugin.packages.PluginRunningList _result = this.getRunningPlugins();
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
case TRANSACTION_isPluginRunning:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.isPluginRunning(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_syncRunningPlugins:
{
data.enforceInterface(descriptor);
com.qihoo360.replugin.packages.PluginRunningList _arg0;
if ((0!=data.readInt())) {
_arg0 = com.qihoo360.replugin.packages.PluginRunningList.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.syncRunningPlugins(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_addToRunningPlugins:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
java.lang.String _arg2;
_arg2 = data.readString();
this.addToRunningPlugins(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_getRunningProcessesByPlugin:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String[] _result = this.getRunningProcessesByPlugin(_arg0);
reply.writeNoException();
reply.writeStringArray(_result);
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements com.qihoo360.replugin.packages.IPluginManagerServer
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
     * 安装一个插件
     * <p>
     * 注意：若为旧插件（p-n开头），则应使用IPluginHost的pluginDownloaded方法
     *
     * @return 安装的插件的PluginInfo对象
     */
@Override public com.qihoo360.replugin.model.PluginInfo install(java.lang.String path) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.qihoo360.replugin.model.PluginInfo _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
mRemote.transact(Stub.TRANSACTION_install, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.qihoo360.replugin.model.PluginInfo.CREATOR.createFromParcel(_reply);
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
/**
     * 卸载一个插件
     * <p>
     * 注意：只针对“纯APK”插件方案
     *
     * @param info 插件信息
     * @return 是否成功卸载插件？
     */
@Override public boolean uninstall(com.qihoo360.replugin.model.PluginInfo info) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((info!=null)) {
_data.writeInt(1);
info.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_uninstall, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * 加载插件列表，方便之后使用
     * <p>
     * TODO 这里只返回"新版插件"，供PmBase使用。将来会合并
     *
     * @return PluginInfo的列表
     */
@Override public java.util.List<com.qihoo360.replugin.model.PluginInfo> load() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.qihoo360.replugin.model.PluginInfo> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_load, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.qihoo360.replugin.model.PluginInfo.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * 更新所有插件列表
     *
     * @return PluginInfo的列表
     */
@Override public java.util.List<com.qihoo360.replugin.model.PluginInfo> updateAll() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.qihoo360.replugin.model.PluginInfo> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_updateAll, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.qihoo360.replugin.model.PluginInfo.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * 设置isUsed状态，并通知所有进程更新
     *
     * @param pluginName 插件名
     * @param used 是否已经使用
     */
@Override public void updateUsed(java.lang.String pluginName, boolean used) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(pluginName);
_data.writeInt(((used)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_updateUsed, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * 获取正在运行的插件列表
     *
     * @return 正在运行的插件名列表
     */
@Override public com.qihoo360.replugin.packages.PluginRunningList getRunningPlugins() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.qihoo360.replugin.packages.PluginRunningList _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getRunningPlugins, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.qihoo360.replugin.packages.PluginRunningList.CREATOR.createFromParcel(_reply);
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
/**
     * 插件是否正在运行？
     *
     * @param pluginName 插件名
     * @param process 指定进程名，如为Null则表示查所有
     * @return 是否在运行？
     */
@Override public boolean isPluginRunning(java.lang.String pluginName, java.lang.String process) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(pluginName);
_data.writeString(process);
mRemote.transact(Stub.TRANSACTION_isPluginRunning, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * 当进程启动时，同步正在运行的插件状态到Server端
     *
     * @param list         正在运行的插件名列表
     */
@Override public void syncRunningPlugins(com.qihoo360.replugin.packages.PluginRunningList list) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((list!=null)) {
_data.writeInt(1);
list.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_syncRunningPlugins, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * 当进程启动时，同步正在运行的插件状态到Server端
     *
     * @param processName  进程名
     * @param pluginName   正在运行的插件名
     */
@Override public void addToRunningPlugins(java.lang.String processName, int pid, java.lang.String pluginName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(processName);
_data.writeInt(pid);
_data.writeString(pluginName);
mRemote.transact(Stub.TRANSACTION_addToRunningPlugins, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.lang.String[] getRunningProcessesByPlugin(java.lang.String pluginName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(pluginName);
mRemote.transact(Stub.TRANSACTION_getRunningProcessesByPlugin, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_install = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_uninstall = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_load = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_updateAll = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_updateUsed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getRunningPlugins = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_isPluginRunning = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_syncRunningPlugins = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_addToRunningPlugins = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getRunningProcessesByPlugin = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
}
/**
     * 安装一个插件
     * <p>
     * 注意：若为旧插件（p-n开头），则应使用IPluginHost的pluginDownloaded方法
     *
     * @return 安装的插件的PluginInfo对象
     */
public com.qihoo360.replugin.model.PluginInfo install(java.lang.String path) throws android.os.RemoteException;
/**
     * 卸载一个插件
     * <p>
     * 注意：只针对“纯APK”插件方案
     *
     * @param info 插件信息
     * @return 是否成功卸载插件？
     */
public boolean uninstall(com.qihoo360.replugin.model.PluginInfo info) throws android.os.RemoteException;
/**
     * 加载插件列表，方便之后使用
     * <p>
     * TODO 这里只返回"新版插件"，供PmBase使用。将来会合并
     *
     * @return PluginInfo的列表
     */
public java.util.List<com.qihoo360.replugin.model.PluginInfo> load() throws android.os.RemoteException;
/**
     * 更新所有插件列表
     *
     * @return PluginInfo的列表
     */
public java.util.List<com.qihoo360.replugin.model.PluginInfo> updateAll() throws android.os.RemoteException;
/**
     * 设置isUsed状态，并通知所有进程更新
     *
     * @param pluginName 插件名
     * @param used 是否已经使用
     */
public void updateUsed(java.lang.String pluginName, boolean used) throws android.os.RemoteException;
/**
     * 获取正在运行的插件列表
     *
     * @return 正在运行的插件名列表
     */
public com.qihoo360.replugin.packages.PluginRunningList getRunningPlugins() throws android.os.RemoteException;
/**
     * 插件是否正在运行？
     *
     * @param pluginName 插件名
     * @param process 指定进程名，如为Null则表示查所有
     * @return 是否在运行？
     */
public boolean isPluginRunning(java.lang.String pluginName, java.lang.String process) throws android.os.RemoteException;
/**
     * 当进程启动时，同步正在运行的插件状态到Server端
     *
     * @param list         正在运行的插件名列表
     */
public void syncRunningPlugins(com.qihoo360.replugin.packages.PluginRunningList list) throws android.os.RemoteException;
/**
     * 当进程启动时，同步正在运行的插件状态到Server端
     *
     * @param processName  进程名
     * @param pluginName   正在运行的插件名
     */
public void addToRunningPlugins(java.lang.String processName, int pid, java.lang.String pluginName) throws android.os.RemoteException;
public java.lang.String[] getRunningProcessesByPlugin(java.lang.String pluginName) throws android.os.RemoteException;
}
