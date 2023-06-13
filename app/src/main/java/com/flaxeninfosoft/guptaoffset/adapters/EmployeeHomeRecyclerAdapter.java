package com.flaxeninfosoft.guptaoffset.adapters;

import android.app.Application;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.LayoutChatLeftBinding;
import com.flaxeninfosoft.guptaoffset.databinding.LayoutChatRightBinding;
import com.flaxeninfosoft.guptaoffset.databinding.SingleAttendanceCardBinding;
import com.flaxeninfosoft.guptaoffset.databinding.SingleDealerCardBinding;
import com.flaxeninfosoft.guptaoffset.databinding.SingleEmployeeCardBinding;
import com.flaxeninfosoft.guptaoffset.databinding.SingleEodCardBinding;
import com.flaxeninfosoft.guptaoffset.databinding.SingleLeaveCardBinding;
import com.flaxeninfosoft.guptaoffset.databinding.SingleLrCardBinding;
import com.flaxeninfosoft.guptaoffset.databinding.SingleOrderCardBinding;
import com.flaxeninfosoft.guptaoffset.databinding.SinglePaymentRequestCardBinding;
import com.flaxeninfosoft.guptaoffset.databinding.SingleSchoolCardBinding;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.EmployeeHistory;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.models.Lr;
import com.flaxeninfosoft.guptaoffset.models.Message;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.models.PaymentRequest;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.utils.SharedPrefs;

import java.util.List;

public class EmployeeHomeRecyclerAdapter extends RecyclerView.Adapter {

    private final List<EmployeeHistory> historyList;
    private final EmployeeHomeClickListener onClickListener;
    private final Application application;

    public EmployeeHomeRecyclerAdapter(List<EmployeeHistory> historyList, Application application, EmployeeHomeClickListener onClickListener) {
        this.historyList = historyList;
        this.onClickListener = onClickListener;
        this.application = application;
    }

    @Override
    public int getItemViewType(int position) {
        int type = historyList.get(position).getType();

        if (type == Constants.TYPE_MESSAGE) {
            Message message = historyList.get(position).getMessage();
            if (message.getSenderId() == SharedPrefs.getInstance(application.getApplicationContext()).getCurrentEmployee().getId()) {
                return Constants.TYPE_MESSAGE_SENT;
            } else {
                return Constants.TYPE_MESSAGE_RECEIVED;
            }
        } else {
            return type;
        }
    }   //    Yeah Boiii!!!

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case Constants.TYPE_ADD_ATTENDANCE:
                SingleAttendanceCardBinding atnCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_attendance_card, parent, false);
                return new SingleAttendanceCardViewHolder(atnCardBinding, onClickListener::onClickCard);
            case Constants.TYPE_ADD_LEAVE:
                SingleLeaveCardBinding leaveCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_leave_card, parent, false);
                return new SingleLeaveCardViewHolder(leaveCardBinding, onClickListener::onClickCard);
            case Constants.TYPE_ADD_SCHOOL:
                SingleSchoolCardBinding schoolCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_school_card, parent, false);
                return new SingleSchoolCardViewHolder(schoolCardBinding, onClickListener::onClickCard);
            case Constants.TYPE_ADD_DEALER:
                SingleDealerCardBinding shopCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_dealer_card, parent, false);
                return new SingleDealerCardViewHolder(shopCardBinding, onClickListener::onClickCard);
            case Constants.TYPE_ADD_ORDER:
                SingleOrderCardBinding orderCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_order_card, parent, false);
                return new SingleOrderCardViewHolder(orderCardBinding, onClickListener::onClickCard);
            case Constants.TYPE_ADD_EOD:
                SingleEodCardBinding eodCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_eod_card, parent, false);
                return new SingleEodCardViewHolder(eodCardBinding, onClickListener::onClickCard);
            case Constants.TYPE_ADD_EMPLOYEE:
                SingleEmployeeCardBinding employeeCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_employee_card, parent, false);
                return new SingleEmployeeCardViewHolder(employeeCardBinding, onClickListener::onCLickCard);
            case Constants.TYPE_ADD_PAYMENT:
                SinglePaymentRequestCardBinding paymentRequestCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_payment_request_card, parent, false);
                return new SinglePaymentCardViewHolder(paymentRequestCardBinding, onClickListener::onClickCard);
            case Constants.TYPE_MESSAGE_SENT:
                LayoutChatRightBinding sendMessageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_chat_right, parent, false);
                return new SingleMessageSentCardViewHolder(sendMessageBinding, onClickListener::onClickCard);
            case Constants.TYPE_MESSAGE_RECEIVED:
                LayoutChatLeftBinding receivedMessageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_chat_left, parent, false);
                return new SingleMessageReceivedCardViewHolder(receivedMessageBinding, onClickListener::onClickCard);
            case Constants.TYPE_LR:
                SingleLrCardBinding lrCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_lr_card, parent, false);
                return new SingleLrCardViewHolder(lrCardBinding, onClickListener::onClickCard);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EmployeeHistory history = historyList.get(position);
        if (history!=null) {
            switch (history.getType()) {
                case Constants.TYPE_ADD_ATTENDANCE:
                    SingleAttendanceCardViewHolder atnViewHolder = (SingleAttendanceCardViewHolder) holder;
                    atnViewHolder.setData(history.getAttendance());
                    break;
                case Constants.TYPE_ADD_LEAVE:
                    SingleLeaveCardViewHolder leaveViewHolder = (SingleLeaveCardViewHolder) holder;
                    leaveViewHolder.setData(history.getLeave());
                    break;
                case Constants.TYPE_ADD_SCHOOL:
                    SingleSchoolCardViewHolder schoolCardViewHolder = (SingleSchoolCardViewHolder) holder;
                    schoolCardViewHolder.setData(history.getSchool());
                    break;
                case Constants.TYPE_ADD_DEALER:
                    SingleDealerCardViewHolder dealerCardViewHolder = (SingleDealerCardViewHolder) holder;
                    dealerCardViewHolder.setData(history.getDealer());
                    break;
                case Constants.TYPE_ADD_ORDER:
                    SingleOrderCardViewHolder orderCardViewHolder = (SingleOrderCardViewHolder) holder;
                    orderCardViewHolder.setData(history.getOrder());
                    break;
                case Constants.TYPE_ADD_EOD:
                    SingleEodCardViewHolder eodCardViewHolder = (SingleEodCardViewHolder) holder;
                    eodCardViewHolder.setData(history.getEod());
                    break;
                case Constants.TYPE_ADD_EMPLOYEE:
                    SingleEmployeeCardViewHolder employeeCardViewHolder = (SingleEmployeeCardViewHolder) holder;
                    employeeCardViewHolder.setData(history.getEmployee());
                    break;
                case Constants.TYPE_ADD_PAYMENT:
                    SinglePaymentCardViewHolder paymentCardViewHolder = (SinglePaymentCardViewHolder) holder;
                    paymentCardViewHolder.setData(history.getPayment());
                    break;
                case Constants.TYPE_MESSAGE:
                    Message message = historyList.get(position).getMessage();
                    if (message.getSenderId() == SharedPrefs.getInstance(application.getApplicationContext()).getCurrentEmployee().getId()) {
                        SingleMessageSentCardViewHolder sendMessageCardViewHolder = (SingleMessageSentCardViewHolder) holder;
                        sendMessageCardViewHolder.setData(history.getMessage());
                    } else {
                        SingleMessageReceivedCardViewHolder receivedCardViewHolder = (SingleMessageReceivedCardViewHolder) holder;
                        receivedCardViewHolder.setData(history.getMessage());
                    }
                    break;
                case Constants.TYPE_LR:
                    SingleLrCardViewHolder lrCardViewHolder = (SingleLrCardViewHolder) holder;
                    lrCardViewHolder.setData(history.getLr());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (historyList == null) {
            return 0;
        }
        return historyList.size();
    }

    public interface EmployeeHomeClickListener {

        void onClickCard(Attendance attendance);

        void onClickCard(Leave leave);

        void onClickCard(School school);

        void onClickCard(Dealer dealer);

        void onClickCard(Order order);

        void onClickCard(Eod eod);

        void onCLickCard(Employee employee);

        void onClickCard(PaymentRequest paymentRequest);

        void onClickCard(Message message);

        void onClickCard(Lr lr);
    }

    public static class SingleAttendanceCardViewHolder extends RecyclerView.ViewHolder {

        private final SingleAttendanceCardBinding binding;

        private final SingleAttendanceCardClickListener clickListener;

        public SingleAttendanceCardViewHolder(@NonNull SingleAttendanceCardBinding binding, SingleAttendanceCardClickListener clickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.clickListener = clickListener;
        }

        public void setData(Attendance attendance) {
            binding.setAttendance(attendance);
            binding.getRoot().setOnClickListener(v -> clickListener.onClickCard(attendance));

            String timeInImage = ApiEndpoints.BASE_URL + attendance.getSnapIn();
            Glide.with(binding.getRoot().getContext()).load(timeInImage).placeholder(R.drawable.loading_image).into(binding.singleAttendanceCardTimeInImage);

            String timeOutImage = ApiEndpoints.BASE_URL + attendance.getSnapOut();
            Glide.with(binding.getRoot().getContext()).load(timeOutImage).placeholder(R.drawable.loading_image).into(binding.singleAttendanceCardTimeOutImage);

            switch (attendance.getPunchStatus()) {
                case 0:
                    binding.attendanceCardStartTime.setVisibility(View.GONE);
                    binding.attendanceCardEndTime.setVisibility(View.GONE);
                case 1:
                    binding.attendanceCardStartTime.setVisibility(View.VISIBLE);
                    binding.attendanceCardEndTime.setVisibility(View.GONE);
                    break;
                case 2:
                    binding.attendanceCardStartTime.setVisibility(View.VISIBLE);
                    binding.attendanceCardEndTime.setVisibility(View.VISIBLE);
            }

        }

        public interface SingleAttendanceCardClickListener {
            void onClickCard(Attendance attendance);
        }

    }

    public static class SingleDealerCardViewHolder extends RecyclerView.ViewHolder {

        private final SingleDealerCardBinding binding;
        private final SingleDealerCardClickListener onCLickListener;

        public SingleDealerCardViewHolder(@NonNull SingleDealerCardBinding binding, SingleDealerCardClickListener onCLickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onCLickListener = onCLickListener;
        }

        public void setData(Dealer dealer) {
            binding.setDealer(dealer);
            binding.getRoot().setOnClickListener(v -> onCLickListener.onClickCard(dealer));

            String imageLink = ApiEndpoints.BASE_URL + dealer.getImage();

            Glide.with(binding.getRoot().getContext()).load(imageLink).placeholder(R.drawable.loading_image).into(binding.singleDealerCardImage);
        }

        public interface SingleDealerCardClickListener {
            void onClickCard(Dealer dealer);
        }
    }

    public static class SingleEmployeeCardViewHolder extends RecyclerView.ViewHolder {

        private final SingleEmployeeCardBinding binding;
        private final SingleEmployeeCardClickListener onClickListener;

        public SingleEmployeeCardViewHolder(@NonNull SingleEmployeeCardBinding binding, SingleEmployeeCardClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public void setData(Employee employee) {
            binding.setEmployee(employee);

            binding.getRoot().setOnClickListener(v -> onClickListener.onCLickCard(employee));
        }

        public interface SingleEmployeeCardClickListener {
            void onCLickCard(Employee employee);
        }

    }

    public static class SingleEodCardViewHolder extends RecyclerView.ViewHolder {

        private final SingleEodCardBinding binding;
        private final SingleEodCardClickListener onCLickListener;

        public SingleEodCardViewHolder(@NonNull SingleEodCardBinding binding, SingleEodCardClickListener onCLickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onCLickListener = onCLickListener;
        }

        public void setData(Eod eod) {
            if (eod == null) {
                return;
            }
            binding.setEod(eod);
            if (eod.getOtherExpense() == null) {
                eod.setOtherExpense("0");
            }
            if (eod.getExpenseImage() != null && !eod.getExpenseImage().isEmpty()) {
                String url = ApiEndpoints.BASE_URL + eod.getExpenseImage();
                Glide.with(binding.getRoot().getContext()).load(url).placeholder(R.drawable.loading_image).into(binding.otherExpenseImageview);
            }
            else {
                binding.otherExpenseImageview.setImageResource(R.drawable.image_not_available);
            }

            if (eod.getExpenseImage() != null && !eod.getExpenseImage().isEmpty()) {
                String url = ApiEndpoints.BASE_URL + eod.getExpenseImage();
                Glide.with(binding.getRoot().getContext()).load(url).placeholder(R.drawable.loading_image).into(binding.otherExpenseImageview);
            }
            else {
                binding.otherExpenseImageview.setImageResource(R.drawable.image_not_available);
            }

            if (eod.getPetrolExpenseImage() != null && !eod.getPetrolExpenseImage().isEmpty()) {
                String url = ApiEndpoints.BASE_URL + eod.getPetrolExpenseImage();
                Glide.with(binding.getRoot().getContext()).load(url).placeholder(R.drawable.loading_image).into(binding.petrolExpenseImageview);
            } else {
//                binding.petrolExpenseImageview.setVisibility(View.GONE);
//                binding.textPetrolexpenseImage.setVisibility(View.GONE);
                binding.petrolExpenseImageview.setImageResource(R.drawable.image_not_available);
            }
            binding.getRoot().setOnClickListener(v -> onCLickListener.onClickCard(eod));
        }

        public interface SingleEodCardClickListener {
            void onClickCard(Eod eod);
        }
    }

    public static class SingleLeaveCardViewHolder extends RecyclerView.ViewHolder {

        private final SingleLeaveCardBinding binding;
        private final SingleLeaveCardClickListener onClickListener;

        public SingleLeaveCardViewHolder(@NonNull SingleLeaveCardBinding binding, SingleLeaveCardClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public void setData(Leave leave) {
            binding.setLeave(leave);
            binding.getRoot().setOnClickListener(v -> onClickListener.onClickCard(leave));
        }

        public interface SingleLeaveCardClickListener {
            void onClickCard(Leave leave);
        }
    }

    public static class SingleOrderCardViewHolder extends RecyclerView.ViewHolder {

        private final SingleOrderCardBinding binding;
        private final SingleOrderCardClickListener onCLickListener;

        public SingleOrderCardViewHolder(@NonNull SingleOrderCardBinding binding, SingleOrderCardClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onCLickListener = onClickListener;
        }

        public void setData(Order order) {
            binding.setOrder(order);
            binding.getRoot().setOnClickListener(v -> onCLickListener.onClickCard(order));
            String imageLink = ApiEndpoints.BASE_URL + order.getSnap();

            Glide.with(binding.getRoot().getContext()).load(imageLink).placeholder(R.drawable.loading_image).into(binding.singleOrderCardImage);
        }

        public interface SingleOrderCardClickListener {
            void onClickCard(Order order);
        }
    }

    public static class SingleSchoolCardViewHolder extends RecyclerView.ViewHolder {

        private final SingleSchoolCardBinding binding;
        private final SingleSchoolCardClickListener onClickListener;

        public SingleSchoolCardViewHolder(@NonNull SingleSchoolCardBinding binding, SingleSchoolCardClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public void setData(School school) {
            binding.setSchool(school);
            binding.getRoot().setOnClickListener(v -> onClickListener.onClickCard(school));

            try {
                String schoolImage = ApiEndpoints.BASE_URL + school.getSnap();
                Glide.with(binding.getRoot().getContext()).load(schoolImage).placeholder(R.drawable.loading_image).into(binding.singleSchoolCardSchoolImage);

                String specimen = ApiEndpoints.BASE_URL + school.getSpecimen();
                Glide.with(binding.getRoot().getContext()).load(specimen).placeholder(R.drawable.loading_image).into(binding.singleSchoolCardSpecimenOrderImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public interface SingleSchoolCardClickListener {
            void onClickCard(School school);
        }
    }

    public static class SinglePaymentCardViewHolder extends RecyclerView.ViewHolder {

        private final SinglePaymentRequestCardBinding binding;
        private final SinglePaymentCardClickListener onClickListener;

        public SinglePaymentCardViewHolder(@NonNull SinglePaymentRequestCardBinding binding, SinglePaymentCardClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public void setData(PaymentRequest payment) {
            try {
                if (payment.getStatus()!=null) {
                    binding.setPaymentRequest(payment);
                    binding.getRoot().setOnClickListener(view -> onClickListener.onClickCard(payment));

                    if (payment.getStatus().equals(Constants.PAYMENT_RECEIVED)) {
                        binding.paymentRequestReceivedLayout.setVisibility(View.VISIBLE);
                    } else {
                        binding.paymentRequestReceivedLayout.setVisibility(View.GONE);
                    }
                }
            }
            catch (Exception e){
                Log.i("Payment","Payment have null data");
            }
        }

        public interface SinglePaymentCardClickListener {
            void onClickCard(PaymentRequest payment);
        }
    }

    public static class SingleMessageSentCardViewHolder extends RecyclerView.ViewHolder {

        private final LayoutChatRightBinding binding;
        private final SingleMessageCardClickListener onClickListener;

        public SingleMessageSentCardViewHolder(@NonNull LayoutChatRightBinding binding, SingleMessageCardClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public void setData(Message message) {
            binding.setMessage(message);
            binding.getRoot().setOnClickListener(view -> onClickListener.onClickCard(message));
        }

        public interface SingleMessageCardClickListener {
            void onClickCard(Message message);
        }
    }

    public static class SingleMessageReceivedCardViewHolder extends RecyclerView.ViewHolder {

        private final LayoutChatLeftBinding binding;
        private final SingleMessageCardClickListener onClickListener;

        public SingleMessageReceivedCardViewHolder(@NonNull LayoutChatLeftBinding binding, SingleMessageCardClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public void setData(Message message) {
            binding.setMessage(message);
            binding.getRoot().setOnClickListener(view -> onClickListener.onClickCard(message));
        }

        public interface SingleMessageCardClickListener {
            void onClickCard(Message message);
        }
    }

    public static class SingleLrCardViewHolder extends RecyclerView.ViewHolder {

        private final SingleLrCardBinding binding;
        private final SingleLrCardClickListener onClickListener;

        public SingleLrCardViewHolder(@NonNull SingleLrCardBinding binding, SingleLrCardClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public void setData(Lr lr) {
            binding.setLr(lr);
            binding.getRoot().setOnClickListener(view -> onClickListener.onClickCard(lr));

            try {
                String image = ApiEndpoints.BASE_URL + lr.getImage();
                Glide.with(binding.getRoot().getContext()).load(image).placeholder(R.drawable.loading_image).into(binding.singleLrCardImage);
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }

        public interface SingleLrCardClickListener {
            void onClickCard(Lr lr);
        }
    }

}
