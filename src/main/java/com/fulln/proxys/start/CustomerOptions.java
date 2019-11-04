/*
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.fulln.proxys.start;

import org.apache.commons.cli.*;

/**
 * @author fulln
 * @project runbrother
 * @description
 * @date 2019/11/1 14:47
 **/
public class CustomerOptions {

	public static void main(String[] args) throws ParseException {
		Options options = new Options();
		options.addOption("job", true, "Job config.");
		options.addOption("id", true, "Job unique id.");
		options.addOption("mode", true, "Job runtime mode.");
		DefaultParser parser = new DefaultParser();
		CommandLine cl = parser.parse(options, args);
		String job = cl.getOptionValue("job");
		String id = cl.getOptionValue("id");
		String mode = cl.getOptionValue("mode");
		System.out.println(String.format("从命令行获取到的参数有 %s %s %s:",job,id,mode));
	}
}
