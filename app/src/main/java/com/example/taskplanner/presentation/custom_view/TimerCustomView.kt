package com.example.taskplanner.presentation.custom_view

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.taskplanner.R
import com.example.taskplanner.databinding.TimerCustomViewLayoutBinding
import com.example.taskplanner.util.timerDate


class TimerCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: TimerCustomViewLayoutBinding = TimerCustomViewLayoutBinding.inflate(
        LayoutInflater.from(context), this, true)

    private lateinit var countDownTimer: CountDownTimer

    fun timer(startTime: Long, endTime: Long): CountDownTimer {

        countDownTimer = object : CountDownTimer(timerDate(startTime, endTime), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var diff = millisUntilFinished
                val secondsInMilli: Long = SECONDS_IN_MILLI
                val minutesInMilli = MINUTES_IN_MILLI
                val hoursInMilli = HOURS_IN_MILLI
                val daysInMilli = DAYS_IN_MILLI

                val elapsedDays = diff / daysInMilli
                diff %= daysInMilli

                val elapsedHours = diff / hoursInMilli
                diff %= hoursInMilli

                val elapsedMinutes = diff / minutesInMilli
                diff %= minutesInMilli

                val elapsedSeconds = diff / secondsInMilli

                val time =
                    "ends in: $elapsedDays days $elapsedHours hs $elapsedMinutes min $elapsedSeconds sec"
                binding.timerTextView.text = time
            }

            override fun onFinish() {
                binding.timerTextView.text = context.getString(R.string.finished_timer_text)
            }
        }
        return countDownTimer
    }

    fun startTimer() = countDownTimer.start()
    fun cancelTimer() = countDownTimer.cancel()

    companion object{
        private const val SECONDS_IN_MILLI = 1000L
        private const val MINUTES_IN_MILLI = SECONDS_IN_MILLI * 60
        private const val HOURS_IN_MILLI = MINUTES_IN_MILLI * 60
        private const val DAYS_IN_MILLI = HOURS_IN_MILLI * 24
    }
}