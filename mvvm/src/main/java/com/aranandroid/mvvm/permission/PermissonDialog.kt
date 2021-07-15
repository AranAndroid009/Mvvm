package com.aranandroid.mvvm.permission

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.aranandroid.mvvm.R
import com.aranandroid.mvvm.databinding.CustomDialogLayoutBinding
import com.permissionx.guolindev.dialog.RationaleDialog

class PermissonDialog(context: Context, val message: String, val permissions: List<String>) :
    RationaleDialog(context, R.style.CustomDialog) {
    private var binding: CustomDialogLayoutBinding? = null


    private val permissionMap = mapOf(
        Manifest.permission.READ_CALENDAR to Manifest.permission_group.CALENDAR,
        Manifest.permission.WRITE_CALENDAR to Manifest.permission_group.CALENDAR,
        Manifest.permission.READ_CALL_LOG to Manifest.permission_group.CALL_LOG,
        Manifest.permission.WRITE_CALL_LOG to Manifest.permission_group.CALL_LOG,
        Manifest.permission.PROCESS_OUTGOING_CALLS to Manifest.permission_group.CALL_LOG,
        Manifest.permission.CAMERA to Manifest.permission_group.CAMERA,
        Manifest.permission.READ_CONTACTS to Manifest.permission_group.CONTACTS,
        Manifest.permission.WRITE_CONTACTS to Manifest.permission_group.CONTACTS,
        Manifest.permission.GET_ACCOUNTS to Manifest.permission_group.CONTACTS,
        Manifest.permission.ACCESS_FINE_LOCATION to Manifest.permission_group.LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION to Manifest.permission_group.LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION to Manifest.permission_group.LOCATION,
        Manifest.permission.RECORD_AUDIO to Manifest.permission_group.MICROPHONE,
        Manifest.permission.READ_PHONE_STATE to Manifest.permission_group.PHONE,
        Manifest.permission.READ_PHONE_NUMBERS to Manifest.permission_group.PHONE,
        Manifest.permission.CALL_PHONE to Manifest.permission_group.PHONE,
        Manifest.permission.ANSWER_PHONE_CALLS to Manifest.permission_group.PHONE,
        Manifest.permission.ADD_VOICEMAIL to Manifest.permission_group.PHONE,
        Manifest.permission.USE_SIP to Manifest.permission_group.PHONE,
        Manifest.permission.ACCEPT_HANDOVER to Manifest.permission_group.PHONE,
        Manifest.permission.BODY_SENSORS to Manifest.permission_group.SENSORS,
        Manifest.permission.ACTIVITY_RECOGNITION to Manifest.permission_group.ACTIVITY_RECOGNITION,
        Manifest.permission.SEND_SMS to Manifest.permission_group.SMS,
        Manifest.permission.RECEIVE_SMS to Manifest.permission_group.SMS,
        Manifest.permission.READ_SMS to Manifest.permission_group.SMS,
        Manifest.permission.RECEIVE_WAP_PUSH to Manifest.permission_group.SMS,
        Manifest.permission.RECEIVE_MMS to Manifest.permission_group.SMS,
        Manifest.permission.READ_EXTERNAL_STORAGE to Manifest.permission_group.STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE to Manifest.permission_group.STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION to Manifest.permission_group.STORAGE
    )

    private val groupSet = HashSet<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate<CustomDialogLayoutBinding>(
            LayoutInflater.from(getContext()),
            R.layout.custom_dialog_layout, null, false
        )
        binding!!.dialog = this
        setContentView(binding!!.root)
        buildPermissionsLayout()
        window?.let {
            val param = it.attributes
            val width = (context.resources.displayMetrics.widthPixels * 0.8).toInt()
            val height = param.height
            it.setLayout(width, height)
        }
    }

    private fun buildPermissionsLayout() {
        for (permission in permissions) {
            val permissionGroup = permissionMap[permission]
            if (permissionGroup != null && !groupSet.contains(permissionGroup)) {
                val textView = LayoutInflater.from(context)
                    .inflate(R.layout.permissions_item,binding?.permissionsLayout, false) as TextView
                textView.text = context.packageManager.getPermissionGroupInfo(permissionGroup, 0)
                    .loadLabel(context.packageManager)
                binding?.permissionsLayout?.addView(textView)
                groupSet.add(permissionGroup)
            }
        }
    }


    override fun getNegativeButton(): View? {
        return  binding?.negativeBtn
    }

    override fun getPositiveButton(): View {
        return  binding!!.positiveBtn
    }

    override fun getPermissionsToRequest(): List<String> {
        return permissions;
    }
}